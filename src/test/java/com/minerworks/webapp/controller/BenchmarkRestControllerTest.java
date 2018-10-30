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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MinerWorksWebApp.class)
public class BenchmarkRestControllerTest {

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
        mockMvc.perform(get("/farm/benchmark/" + TEST_FARM_CODE)).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farm/benchmark/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_addBenchmarkResult_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String benchmarkClientEntityJson = "{\n" +
                "    \"gpuUUID\": \"" + TEST_GPU_UUID + "\",\n" +
                "    \"algorithmName\": \"" + TEST_ALGORITHM_NAME + "\",\n" +
                "    \"minerName\": \"" + TEST_MINER_NAME + "\",\n" +
                "    \"hashrate\": " + TEST_HASHRATE + "\n" +
                "}";

        mockMvc.perform(post("/farm/benchmark")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(benchmarkClientEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/benchmark/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].gpuCardEntity.gpuUUID", is(TEST_GPU_UUID)))
                .andExpect(jsonPath("$[0].algorithmEntity.name", is(TEST_ALGORITHM_NAME)))
                .andExpect(jsonPath("$[0].minerEntity.name", is(TEST_MINER_NAME)))
                .andExpect(jsonPath("$[0].hashrate", is(TEST_HASHRATE.intValue())));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_updateBenchmarkResult_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String benchmarkClientEntityJson = "{\n" +
                "    \"gpuUUID\": \"" + TEST_GPU_UUID + "\",\n" +
                "    \"algorithmName\": \"" + TEST_ALGORITHM_NAME + "\",\n" +
                "    \"minerName\": \"" + TEST_MINER_NAME + "\",\n" +
                "    \"hashrate\": " + TEST_HASHRATE_UPDATED + "\n" +
                "}";

        mockMvc.perform(put("/farm/benchmark")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(benchmarkClientEntityJson)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farm/benchmark/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].gpuCardEntity.gpuUUID", is(TEST_GPU_UUID)))
                .andExpect(jsonPath("$[0].algorithmEntity.name", is(TEST_ALGORITHM_NAME)))
                .andExpect(jsonPath("$[0].minerEntity.name", is(TEST_MINER_NAME)))
                .andExpect(jsonPath("$[0].hashrate", is(TEST_HASHRATE_UPDATED.intValue())));
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_deleteBenchmarkResultsForFarm_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(delete("/farm/benchmark/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/farm/benchmark/" + TEST_FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

}