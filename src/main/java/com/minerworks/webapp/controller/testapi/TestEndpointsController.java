package com.minerworks.webapp.controller.testapi;

import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.utils.test.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestEndpointsController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/echo")
    @ResponseBody
    public String test(){
        return "echo";
    }

    @RequestMapping(value="/currentUserPrincipal", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserPrincipal(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "/currentUserAuthenticationName", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserAuthenticationName(Authentication authentication) {
        return authentication.getName();
    }

    @RequestMapping(value="/echoFarmEntity", method = RequestMethod.GET)
    @ResponseBody
    public FarmEntity echoFarmEntity() {
        User user = userRepository.findByEmail("email@email.com");
        return TestEntity.getFarmEntity(user);
    }

    @RequestMapping(value="/echoGpuCardEntitySet", method = RequestMethod.GET)
    @ResponseBody
    public Set<GpuCardEntity> echoGpuCardEntitySet() {

        User user = userRepository.findByEmail("email@email.com");
        GpuCardEntity gpuCardEntity = new GpuCardEntity();
        return Collections.singleton(gpuCardEntity);
    }

    @RequestMapping(value="/echoBenchmarkClientEntity", method = RequestMethod.GET)
    @ResponseBody
    public BenchmarkDTO echoBenchmarkClientEntity() {
        return TestEntity.getBenchmarkClientEntity();
    }

    @RequestMapping(value="/echoMiningCardEntity", method = RequestMethod.GET)
    @ResponseBody
    public MiningCardEntity echoMiningCardEntity() {
        return TestEntity.getMiningCardEntity();
    }

    @RequestMapping(value="/echoFarmSettingsEntity", method = RequestMethod.GET)
    @ResponseBody
    public FarmSettingsEntity echoFarmSettingsEntity() {
        return TestEntity.getFarmSettingsEntity();
    }

}
