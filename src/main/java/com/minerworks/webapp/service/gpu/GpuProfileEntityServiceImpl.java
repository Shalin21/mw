package com.minerworks.webapp.service.gpu;

import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.gpu.GpuProfileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GpuProfileEntityServiceImpl implements GpuProfileEntityService {

    @Autowired
    private GpuProfileEntityRepository gpuProfileEntityRepository;

    // TODO: this method both save and update
    @Override
    public GpuProfileEntity save(User user, GpuProfileEntity gpuProfileEntity) {
        gpuProfileEntity.setUser(user);
        return gpuProfileEntityRepository.save(gpuProfileEntity);
    }

    @Override
    public List<GpuProfileEntity> findAllByUserAndGpuCardName(User user, String gpuCardName) {
        return gpuProfileEntityRepository.findAllByUserAndGpuCardName(user, gpuCardName);
    }

    @Override
    public boolean userHasGpuProfile(User user, GpuProfileEntity gpuProfileEntity) {
        GpuProfileEntity gpuProfileEntityCurrent = gpuProfileEntityRepository.findGpuProfileEntityByUserAndGpuCardNameAndGpuProfileName(user, gpuProfileEntity.getGpuCardName(), gpuProfileEntity.getGpuCardName());
        return gpuProfileEntityCurrent != null;
    }

    @Override
    public boolean userHasGpuProfile(User user, String gpuCardName, String gpuProfileName) {
        GpuProfileEntity gpuProfileEntity = gpuProfileEntityRepository.findGpuProfileEntityByUserAndGpuCardNameAndGpuProfileName(user, gpuCardName, gpuProfileName);
        return gpuProfileEntity != null;
    }

    @Override
    public void delete(User user, String gpuCardName, String gpuProfileName) {
        GpuProfileEntity gpuProfileEntity = gpuProfileEntityRepository.findGpuProfileEntityByUserAndGpuCardNameAndGpuProfileName(user, gpuCardName, gpuProfileName);
        gpuProfileEntityRepository.delete(gpuProfileEntity);
    }

    @Override
    public void delete(GpuProfileEntity gpuProfileEntity) {
        gpuProfileEntityRepository.delete(gpuProfileEntity);
    }

}
