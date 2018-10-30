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
public class FarmSettingsRestControllerClientTest {

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
        mockMvc.perform(get("/farm/settings/" + TEST_FARM_CODE)).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_createFarmSettingsEntity_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String farmSettingsEntityJson = "{\n" +
                "    \"theme\": \"dark\",\n" +
                "    \"language\": \"en\",\n" +
                "    \"runWithWindows\": true,\n" +
                "    \"minimizeToTray\": false,\n" +
                "    \"hideMiningWindow\": false,\n" +
                "    \"autostartMining\": false,\n" +
                "    \"idleMining\": false,\n" +
                "    \"idleMiningSeconds\": 0,\n" +
                "    \"currencyForDailyRevenue\": \"USD\",\n" +
                "    \"profitabilityCheckTimeMinutes\": 2,\n" +
                "    \"chartsRefreshTimeSeconds\": 2\n" +
                "}";

        mockMvc.perform(post("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(farmSettingsEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.theme", is("dark")))
                .andExpect(jsonPath("$.language", is("en")))
                .andExpect(jsonPath("$.runWithWindows", is(true)))
                .andExpect(jsonPath("$.minimizeToTray", is(false)))
                .andExpect(jsonPath("$.hideMiningWindow", is(false)))
                .andExpect(jsonPath("$.autostartMining", is(false)))
                .andExpect(jsonPath("$.idleMining", is(false)))
                .andExpect(jsonPath("$.idleMiningSeconds", is(0)))
                .andExpect(jsonPath("$.currencyForDailyRevenue", is("USD")))
                .andExpect(jsonPath("$.profitabilityCheckTimeMinutes", is(2)))
                .andExpect(jsonPath("$.chartsRefreshTimeSeconds", is(2)));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_updateFarmSettingsEntity_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String farmSettingsEntityJson = "{\n" +
                "    \"theme\": \"light\",\n" +
                "    \"language\": \"ru\",\n" +
                "    \"runWithWindows\": true,\n" +
                "    \"minimizeToTray\": false,\n" +
                "    \"hideMiningWindow\": false,\n" +
                "    \"autostartMining\": false,\n" +
                "    \"idleMining\": false,\n" +
                "    \"idleMiningSeconds\": 0,\n" +
                "    \"currencyForDailyRevenue\": \"USD\",\n" +
                "    \"profitabilityCheckTimeMinutes\": 2,\n" +
                "    \"chartsRefreshTimeSeconds\": 2\n" +
                "}";

        mockMvc.perform(put("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(farmSettingsEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.theme", is("light")))
                .andExpect(jsonPath("$.language", is("ru")))
                .andExpect(jsonPath("$.runWithWindows", is(true)))
                .andExpect(jsonPath("$.minimizeToTray", is(false)))
                .andExpect(jsonPath("$.hideMiningWindow", is(false)))
                .andExpect(jsonPath("$.autostartMining", is(false)))
                .andExpect(jsonPath("$.idleMining", is(false)))
                .andExpect(jsonPath("$.idleMiningSeconds", is(0)))
                .andExpect(jsonPath("$.currencyForDailyRevenue", is("USD")))
                .andExpect(jsonPath("$.profitabilityCheckTimeMinutes", is(2)))
                .andExpect(jsonPath("$.chartsRefreshTimeSeconds", is(2)));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_deleteFarmSettingsEntity_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(delete("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/farm/settings/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}