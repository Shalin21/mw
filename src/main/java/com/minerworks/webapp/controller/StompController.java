package com.minerworks.webapp.controller;


import com.minerworks.webapp.service.MessageSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StompController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private Map<String, String> userMap;

    @Autowired
    private MessageSender sender;

    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    public void greeting(SimpMessageHeaderAccessor headerAccessor, String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        JSONObject jsonObject = new JSONObject(message);

        // return "Hello, " + HtmlUtils.htmlEscape(message + "!");
//        messagingTemplate.convertAndSend("/topic/hi", "Hi" +message+"boi");
//        messagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/greetings", "Hi "+message);
        //String agentId = jsonObject.get("name").toString().substring(2, jsonObject.get("name").toString().length()-2);
        String agentId = jsonObject.get("Name").toString();
        String s_id = userMap.get(agentId);
        System.out.println("Sending message to user2. Target session: " + s_id);
        sender.sendEventToClient(message, s_id);
        System.out.println(message);
        System.out.println("Message sent");
//        System.out.println(headerAccessor.getSessionId());
    }

}
