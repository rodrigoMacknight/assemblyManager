package com.mk.assemblyManager.repository;

import com.mk.assemblyManager.domain.Associate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupDB implements ApplicationRunner {

    private final AssociateRepository associateRepository;

    public StartupDB(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public void createAssociates(int amount) {
        long cpf = 39566743831L;
        for (int i = 0; i < amount; i++) {
            associateRepository.save(new Associate((long) i, null, (cpf++) + ""));
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAssociates(5);
    }
}
