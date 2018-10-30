package com.minerworks.webapp.controller.api.v1.workstation.farms.benchmark;

import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.benchmark.BenchmarkResultEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.benchmark.BenchmarkEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workstation/farms/benchmark")
public class BenchmarkRestController {

    private Logger logger = LoggerFactory.getLogger(BenchmarkRestController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BenchmarkEntityService benchmarkEntityService;

    @GetMapping(value = "/{farmUUID}")
    @ResponseBody
    public ResponseEntity<List<BenchmarkResultEntity>> getBenchmarkResultsForFarm(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        User user = userRepository.findByEmail(principal.getName());
        logger.debug("Get Benchmark Results for user {} and farm (farm code) {}", user, farmUUID);
        return ResponseEntity.ok(benchmarkEntityService.findAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(user, farmUUID));
    }

    /**
     * Farm code param is absent because gpu UUID is in request body.
     *
     * @param principal
     * @param benchmarkDTO
     */
    @PostMapping
    public void addBenchmarkResult(Principal principal, @RequestBody BenchmarkDTO benchmarkDTO) {
        User user = userRepository.findByEmail(principal.getName());
        logger.debug("Adding Benchmark result for user {}", user);
        benchmarkEntityService.createFromBenchmarkClientEntity(user, benchmarkDTO);
    }

    /**
     * Farm code param is absent because gpu UUID is in request body.
     *
     * @param principal
     * @param benchmarkDTO
     */
    @PutMapping
    public void updateBenchmarkResult(Principal principal, @RequestBody BenchmarkDTO benchmarkDTO) {
        User user = userRepository.findByEmail(principal.getName());
        logger.debug("Updating Benchmark result for user {}", user);
        benchmarkEntityService.updateFromBenchmarkClientEntity(user, benchmarkDTO);
    }

    @DeleteMapping("/{farmUUID}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBenchmarkResultsForFarm(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        User user = userRepository.findByEmail(principal.getName());
        benchmarkEntityService.deleteBenchmarkResults(user, farmUUID);
    }

}
