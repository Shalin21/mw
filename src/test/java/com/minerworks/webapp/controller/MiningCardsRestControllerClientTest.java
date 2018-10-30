package com.minerworks.webapp.controller;

import com.minerworks.webapp.MinerWorksWebApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MinerWorksWebApp.class)
public class MiningCardsRestControllerClientTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CLIENT_ID = "client";
    private static final String CLIENT_SECRET = "password";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final String USERNAME = "email@email.com";
    private static final String PASSWORD = "password";

    private static final String TEST_FARM_CODE = "test-farm-code";
    private static final String TEST_GPU_UUID = "test-gpu-uuid";
    private static final String TEST_ALGORITHM_NAME = "dagger_hashimoto";
    private static final String TEST_MINER_NAME = "claymore_dual";
    private static final Long TEST_HASHRATE = 11111L;
    private static final Long TEST_HASHRATE_UPDATED = 22222L;

    private static Boolean active_TEST = Boolean.FALSE;
    private static Boolean miningNow_TEST = Boolean.FALSE;
    private static Boolean dualMining_TEST = Boolean.FALSE;
    private static String poolUsernameCoinOne_TEST = null;
    private static String workerPasswordCoinOne_TEST = null;
    private static String workerNameCoinOne_TEST = "test-worker-name";
    private static String workerEmailCoinOne_TEST = "test@test.com";
    private static String coinOne_TEST = "Ethereum";
    private static String poolCoinOne_TEST = "Nanopool";
    private static String algorithmCoinOne_TEST = "dagger_hashimoto";
    private static String serverCoinOne_TEST = "Europe";
    private static String serverAddressCoinOne_TEST = "test.com";
    private static String portCoinOne_TEST = "Stratum";
    private static String portAddressCoinOne_TEST = "1111";
    private static String paymentIdCoinOne_TEST = null;
    private static String walletAddressCoinOne_TEST = "test-wallet-address";

    private String poolCoinOne_TEST_UPDATED = "DwarfPool";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @SuppressWarnings("Duplicates")
    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_getAll_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/farm/miningCard/" + TEST_FARM_CODE)).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_getAll_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_addMiningCard_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String miningCardClientEntityJson = "{\n" +
                "    \"active\": false,\n" +
                "    \"miningNow\": false,\n" +
                "    \"dualMining\": false,\n" +
                "    \"poolUsernameCoinOne\": null,\n" +
                "    \"workerPasswordCoinOne\": null,\n" +
                "    \"workerNameCoinOne\": \"test-worker-name\",\n" +
                "    \"workerEmailCoinOne\": \"test@test.com\",\n" +
                "    \"coinOne\": \"" + coinOne_TEST + "\",\n" +
                "    \"poolCoinOne\": \"" + poolCoinOne_TEST + "\",\n" +
                "    \"algorithmCoinOne\": \"dagger_hashimoto\",\n" +
                "    \"serverCoinOne\": \"Europe\",\n" +
                "    \"serverAddressCoinOne\": \"test.com\",\n" +
                "    \"portCoinOne\": \"Stratum\",\n" +
                "    \"portAddressCoinOne\": \"1111\",\n" +
                "    \"paymentIdCoinOne\": null,\n" +
                "    \"walletAddressCoinOne\": \"test-wallet-address\",\n" +
                "    \"poolUsernameCoinTwo\": null,\n" +
                "    \"workerPasswordCoinTwo\": null,\n" +
                "    \"workerNameCoinTwo\": null,\n" +
                "    \"workerEmailCoinTwo\": null,\n" +
                "    \"coinTwo\": null,\n" +
                "    \"poolCoinTwo\": null,\n" +
                "    \"algorithmCoinTwo\": null,\n" +
                "    \"serverCoinTwo\": null,\n" +
                "    \"serverAddressCoinTwo\": null,\n" +
                "    \"portCoinTwo\": null,\n" +
                "    \"portAddressCoinTwo\": null,\n" +
                "    \"paymentIdCoinTwo\": null,\n" +
                "    \"walletAddressCoinTwo\": null\n" +
                "}";

        mockMvc.perform(post("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(miningCardClientEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].coinOne", is(coinOne_TEST)))
                .andExpect(jsonPath("$[0].poolCoinOne", is(poolCoinOne_TEST)))
                .andExpect(jsonPath("$[0].coinTwo", is(nullValue())))
                .andExpect(jsonPath("$[0].poolCoinTwo", is(nullValue())));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_updateMiningCard_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String miningCardClientEntityJson = "{\n" +
                "    \"active\": false,\n" +
                "    \"miningNow\": false,\n" +
                "    \"dualMining\": false,\n" +
                "    \"poolUsernameCoinOne\": null,\n" +
                "    \"workerPasswordCoinOne\": null,\n" +
                "    \"workerNameCoinOne\": \"test-worker-name\",\n" +
                "    \"workerEmailCoinOne\": \"test@test.com\",\n" +
                "    \"coinOne\": \"" + coinOne_TEST + "\",\n" +
                "    \"poolCoinOne\": \"" + poolCoinOne_TEST_UPDATED + "\",\n" +
                "    \"algorithmCoinOne\": \"dagger_hashimoto\",\n" +
                "    \"serverCoinOne\": \"Europe\",\n" +
                "    \"serverAddressCoinOne\": \"test.com\",\n" +
                "    \"portCoinOne\": \"Stratum\",\n" +
                "    \"portAddressCoinOne\": \"1111\",\n" +
                "    \"paymentIdCoinOne\": null,\n" +
                "    \"walletAddressCoinOne\": \"test-wallet-address\",\n" +
                "    \"poolUsernameCoinTwo\": null,\n" +
                "    \"workerPasswordCoinTwo\": null,\n" +
                "    \"workerNameCoinTwo\": null,\n" +
                "    \"workerEmailCoinTwo\": null,\n" +
                "    \"coinTwo\": null,\n" +
                "    \"poolCoinTwo\": null,\n" +
                "    \"algorithmCoinTwo\": null,\n" +
                "    \"serverCoinTwo\": null,\n" +
                "    \"serverAddressCoinTwo\": null,\n" +
                "    \"portCoinTwo\": null,\n" +
                "    \"portAddressCoinTwo\": null,\n" +
                "    \"paymentIdCoinTwo\": null,\n" +
                "    \"walletAddressCoinTwo\": null\n" +
                "}";

        mockMvc.perform(put("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(miningCardClientEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].coinOne", is(coinOne_TEST)))
                .andExpect(jsonPath("$[0].poolCoinOne", is(poolCoinOne_TEST_UPDATED)))
                .andExpect(jsonPath("$[0].coinTwo", is(nullValue())))
                .andExpect(jsonPath("$[0].poolCoinTwo", is(nullValue())));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_deleteMiningCard_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String miningCardClientEntityJson = "{\n" +
                "    \"active\": false,\n" +
                "    \"miningNow\": false,\n" +
                "    \"dualMining\": false,\n" +
                "    \"poolUsernameCoinOne\": null,\n" +
                "    \"workerPasswordCoinOne\": null,\n" +
                "    \"workerNameCoinOne\": \"test-worker-name\",\n" +
                "    \"workerEmailCoinOne\": \"test@test.com\",\n" +
                "    \"coinOne\": \"" + coinOne_TEST + "\",\n" +
                "    \"poolCoinOne\": \"" + poolCoinOne_TEST + "\",\n" +
                "    \"algorithmCoinOne\": \"dagger_hashimoto\",\n" +
                "    \"serverCoinOne\": \"Europe\",\n" +
                "    \"serverAddressCoinOne\": \"test.com\",\n" +
                "    \"portCoinOne\": \"Stratum\",\n" +
                "    \"portAddressCoinOne\": \"1111\",\n" +
                "    \"paymentIdCoinOne\": null,\n" +
                "    \"walletAddressCoinOne\": \"test-wallet-address\",\n" +
                "    \"poolUsernameCoinTwo\": null,\n" +
                "    \"workerPasswordCoinTwo\": null,\n" +
                "    \"workerNameCoinTwo\": null,\n" +
                "    \"workerEmailCoinTwo\": null,\n" +
                "    \"coinTwo\": null,\n" +
                "    \"poolCoinTwo\": null,\n" +
                "    \"algorithmCoinTwo\": null,\n" +
                "    \"serverCoinTwo\": null,\n" +
                "    \"serverAddressCoinTwo\": null,\n" +
                "    \"portCoinTwo\": null,\n" +
                "    \"portAddressCoinTwo\": null,\n" +
                "    \"paymentIdCoinTwo\": null,\n" +
                "    \"walletAddressCoinTwo\": null\n" +
                "}";

        mockMvc.perform(delete("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(miningCardClientEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/farm/miningCard/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

}