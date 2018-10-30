package com.minerworks.webapp.utils;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.*;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.repository.mining.*;
import com.minerworks.webapp.service.FarmEntityService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minerworks.webapp.repository.mining.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PopulateDB {

    @Autowired
    private CoinEntityRepository coinEntityRepository;

    @Autowired
    private PoolEntityRepository poolEntityRepository;

    @Autowired
    private PoolCoinEntityRepository poolCoinEntityRepository;

    @Autowired
    private ServerEntityRepository serverEntityRepository;

    @Autowired
    private PortEntityRepository portEntityRepository;


    @Test
    public void poolsFromJson_Output() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootArray = mapper.readTree(new File("e:\\pools.json"));


        for(JsonNode root : rootArray){

            String poolName = root.path("poolName").asText();
            boolean requiresEmail = root.path("requiresEmail").asBoolean();
            boolean requiresPoolUsername = root.path("requiresPoolUsername").asBoolean();
            boolean requiresPassword = root.path("requiresPassword").asBoolean();
            boolean requiresWalletAddress = root.path("requiresWalletAddress").asBoolean();

            System.out.println("poolName: " + poolName);
            System.out.println("requiresEmail: " + requiresEmail);
            System.out.println("requiresPoolUsername: " + requiresPoolUsername);
            System.out.println("requiresPassword: " + requiresPassword);
            System.out.println("requiresWalletAddress: " + requiresWalletAddress);

            JsonNode coinsNode = root.path("coins");

            for (JsonNode coinNode : coinsNode) {
                String coinName = coinNode.path("coinName").asText();

                System.out.println(coinName);

                JsonNode serversNode = coinNode.path("servers");

                Map<String, String> serverMap = mapper.convertValue(serversNode, Map.class);

                for (Map.Entry<String, String> serverEntry : serverMap.entrySet()) {
                    String serverName = serverEntry.getKey();
                    String serverAddress = serverEntry.getValue();

                    System.out.println("serverName: " + serverName);
                    System.out.println("serverAddress: " + serverAddress);
                }

                JsonNode portsNode = coinNode.path("ports");

                Map<String, String> portMap = mapper.convertValue(portsNode, Map.class);

                for (Map.Entry<String, String> portEntry : portMap.entrySet()) {
                    String portName = portEntry.getKey();
                    String portAddress = portEntry.getValue();

                    System.out.println("portName: " + portName);
                    System.out.println("portAddress: " + portAddress);
                }

            }

        }

    }


    @SuppressWarnings("Duplicates")
    @Test
    public void importPoolsFromJson() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootArray = mapper.readTree(new File("e:\\pools.json"));


        for(JsonNode root : rootArray){

            String poolName = root.path("poolName").asText();
            boolean requiresEmail = root.path("requiresEmail").asBoolean();
            boolean requiresPoolUsername = root.path("requiresPoolUsername").asBoolean();
            boolean requiresPassword = root.path("requiresPassword").asBoolean();
            boolean requiresWalletAddress = root.path("requiresWalletAddress").asBoolean();

            PoolEntity poolEntity = new PoolEntity();
            poolEntity.setPoolName(poolName);
            poolEntity.setRequiresEmail(requiresEmail);
            poolEntity.setRequiresPoolUsername(requiresPoolUsername);
            poolEntity.setRequiresPassword(requiresPassword);
            poolEntity.setRequiresWalletAddress(requiresWalletAddress);

            JsonNode coinsNode = root.path("coins");

            for (JsonNode coinNode : coinsNode) {
                String coinName = coinNode.path("coinName").asText();

                PoolCoinEntity poolCoinEntity = new PoolCoinEntity();
                poolCoinEntity.setCoinEntity(coinEntityRepository.getByCoinName(coinName));

                JsonNode serversNode = coinNode.path("servers");

                Map<String, String> serverMap = mapper.convertValue(serversNode, Map.class);

                for (Map.Entry<String, String> serverEntry : serverMap.entrySet()) {
                    String serverName = serverEntry.getKey();
                    String serverAddress = serverEntry.getValue();

                    ServerEntity serverEntity = new ServerEntity();
                    serverEntity.setServerName(serverName);
                    serverEntity.setServerAddress(serverAddress);

                    serverEntityRepository.save(serverEntity);

                    poolCoinEntity.getServers().add(serverEntity);
                }

                JsonNode portsNode = coinNode.path("ports");

                Map<String, String> portMap = mapper.convertValue(portsNode, Map.class);

                for (Map.Entry<String, String> portEntry : portMap.entrySet()) {
                    String portName = portEntry.getKey();
                    String portAddress = portEntry.getValue();

                    PortEntity portEntity = new PortEntity();
                    portEntity.setPortName(portName);
                    portEntity.setPortAddress(Integer.valueOf(portAddress));

                    portEntityRepository.save(portEntity);

                    poolCoinEntity.getPorts().add(portEntity);
                }

                poolEntity.addPoolCoinEntity(poolCoinEntity);
            }

            poolEntityRepository.save(poolEntity);

        }

    }

    @Test
    public void pools_findAll() {
        System.out.println(poolEntityRepository.findAll());
    }

}
