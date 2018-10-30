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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MinerWorksWebApp.class)
//@ActiveProfiles("mvc")
public class FarmsRestControllerClientTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CLIENT_ID = "client";
    private static final String CLIENT_SECRET = "password";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final String CARD_ID = "0";

    private static final String USERNAME = "email@email.com";

    private static final String PASSWORD = "password";

    private static final String FARM_NAME = "test-farm-name";
    private static final String FARM_CODE = "test-farm-code";


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        // @formatter:off

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        // @formatter:on

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/farms/getAll")).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farms/getAll")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String gpuProfileString = "{\n" +
                "    \"name\": \"" + FARM_NAME + "\",\n" +
                "    \"farmUUID\": \"test-farm-code\"\n" +
                "}";

        mockMvc.perform(post("/farms/add")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(gpuProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farms/getByFarmUUID/" + FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.name", is(FARM_NAME)));
    }

    @Test
    public void deleteTest() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        mockMvc.perform(delete("/farms/delete/" + FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/farms/getByFarmUUID/" + FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    public void update() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String gpuProfileString = "[\n" +
                "    {\n" +
                "        \"id\": 0,\n" +
                "        \"uuid\": null,\n" +
                "        \"name\": null,\n" +
                "        \"gpuCardParamsEntity\": null,\n" +
                "        \"gpuProfileEntity\": null,\n" +
                "        \"coreVoltageRangeMin\": 0,\n" +
                "        \"coreVoltageRangeMax\": 0,\n" +
                "        \"powerLimitRangeMinPercentage\": 0,\n" +
                "        \"powerLimitRangeMaxPercentage\": 0,\n" +
                "        \"tempLimitRangeMin\": 0,\n" +
                "        \"tempLimitRangeMax\": 0,\n" +
                "        \"coreClockRangeMin\": 0,\n" +
                "        \"coreClockRangeMax\": 0,\n" +
                "        \"memoryClockRangeMin\": 0,\n" +
                "        \"memoryClockRangeMax\": 0,\n" +
                "        \"fanSpeedRangeMinPercentage\": 0,\n" +
                "        \"fanSpeedRangeMaxPercentage\": 0\n" +
                "    }\n" +
                "]";

        mockMvc.perform(post("/farms/add")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(gpuProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        mockMvc.perform(get("/farms/getByFarmUUID/" + FARM_CODE)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.name", is(FARM_NAME)));
    }


}