package com.minerworks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.Map;

@Service
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    Map<String, String> userMap;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        System.out.println("THIS HEADERS: ");
        System.out.println(sha.getMessageHeaders().toString());
        String agentId = sha.getNativeHeader("Login").get(0);
        String sessionId = sha.getSessionId();

        /** add new session to registry */
        userMap.put(agentId,sessionId);
//        webAgentSessionRegistry.addSession(agentId,sessionId);
        System.out.println(userMap.toString());
        //debug: show connected to stdout
//        webAgentSessionRegistry.show();

    }
}