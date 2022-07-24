package com.mk.assemblyManager.repository;

import com.mk.assemblyManager.domain.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly, Long> {

    public List<Assembly> findByRulingName(String name);

}
