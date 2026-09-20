package com.faisalrmdhn.GaraseKu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;

public interface MasterRoleRepository extends JpaRepository<MasterRole, String> {
  Optional<MasterRole> findByVrolenameIgnoreCase(String vrolename);
}
