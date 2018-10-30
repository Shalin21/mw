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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MinerWorksWebApp.class)
//@ActiveProfiles("mvc")
public class GpuProfileRestControllerTest {

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

    private static final String PROFILE_NAME = "Test Profile";


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
        mockMvc.perform(get("/farms/gpuProfile/get")).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);
        mockMvc.perform(get("/farms/gpuProfile/get")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String gpuProfileString = "{\n" +
                "    \"id\": 0,\n" +
                "    \"gpuCardName\": \"NVIDIA 1070\",\n" +
                "    \"profileName\": \"" + PROFILE_NAME + "\",\n" +
                "    \"gpuCardParamsEntity\": {\n" +
                "        \"id\": 0,\n" +
                "        \"coreVoltage\": 10,\n" +
                "        \"powerLimit\": 11,\n" +
                "        \"tempLimit\": 12,\n" +
                "        \"coreClock\": 13,\n" +
                "        \"memoryClock\": 14,\n" +
                "        \"fanSpeedPercentage\": 15,\n" +
                "        \"autoFanSpeed\": false\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/farms/gpuProfile/add")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(gpuProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/farms/gpuProfile/get")
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].profileName", is(PROFILE_NAME)));

    }

    @Test
    public void delete() throws Exception {
        final String accessToken = obtainAccessToken(USERNAME, PASSWORD);

        String gpuProfileString = "{\n" +
                "    \"id\": 0,\n" +
                "    \"gpuCardName\": \"NVIDIA 1070\",\n" +
                "    \"profileName\": \"" + PROFILE_NAME + "\",\n" +
                "    \"gpuCardParamsEntity\": {\n" +
                "        \"id\": 0,\n" +
                "        \"coreVoltage\": 10,\n" +
                "        \"powerLimit\": 11,\n" +
                "        \"tempLimit\": 12,\n" +
                "        \"coreClock\": 13,\n" +
                "        \"memoryClock\": 14,\n" +
                "        \"fanSpeedPercentage\": 15,\n" +
                "        \"autoFanSpeed\": false\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/farms/gpuProfile/delete")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(gpuProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/farms/gpuProfile/get")
                .param("email", CARD_ID)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string("[]"));

    }


}