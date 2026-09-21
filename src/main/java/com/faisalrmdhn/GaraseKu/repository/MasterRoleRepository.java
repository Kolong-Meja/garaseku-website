package com.faisalrmdhn.GaraseKu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;

public interface MasterRoleRepository extends JpaRepository<MasterRole, String> {
  @Query("SELECT r FROM MasterRole r WHERE LOWER(r.vrolename) = LOWER(:vrolename)")
  Optional<MasterRole> findByVrolenameIgnoreCase(String vrolename);
}