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

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MinerWorksWebApp.class)
public class MiningRestControllerTest {

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
    private static final String TEST_COIN_NAME = "Ethereum";
    private static final String TEST_GPU_UUID = "test-gpu-uuid";
    private static final String TEST_ALGORITHM_NAME = "dagger_hashimoto";
    private static final String TEST_MINER_NAME = "claymore_dual";

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
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/farm/miningFields/primaryCoins/" + TEST_FARM_CODE)).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farm/miningFields/primaryCoins/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    /*
    has item - make sure not empty
     */
    @Test
    public void givenToken_whenPostGetSecureRequest_getPrimaryCoinList_notEmpty_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(get("/farm/miningFields/primaryCoins/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.*", hasItem("Ethereum")));
    }


    /*
   has item - make sure not empty
    */
    @Test
    public void givenToken_whenPostGetSecureRequest_getSecondaryCoinList_notEmpty_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(get("/farm/miningFields/secondaryCoins/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.*", hasItem("SiaCoin")));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_getPoolListForCoin_notEmpty_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(get("/farm/miningFields/pool/coins/" + TEST_COIN_NAME)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.*", hasItem("Nanopool")));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_getPoolEntity_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(get("/farm/miningFields/pool/" + "Nanopool")
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.poolName", is("Nanopool")));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_getCoinAlgorithm_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(get("/farm/miningFields/coin/algorithm/" + "Ethereum")
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string("dagger_hashimoto"));
    }

}