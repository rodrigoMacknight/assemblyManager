package com.mk.assemblyManager.repository;

import com.mk.assemblyManager.domain.Assembly;
import com.mk.assemblyManager.domain.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository  extends JpaRepository<Associate, Long> {
}
