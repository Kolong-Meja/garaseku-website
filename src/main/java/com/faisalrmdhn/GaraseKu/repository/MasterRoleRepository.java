package com.faisalrmdhn.GaraseKu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;

public interface MasterRoleRepository extends JpaRepository<MasterRole, String> {

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users")
  List<MasterRole> findAllWithUsers();

  @Query("SELECT r FROM MasterRole WHERE r.vroleid = :vroleid")
  Optional<MasterRole> findById(String vroleid);

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users WHERE r.vroleid = :vroleid")
  Optional<MasterRole> findByIdWithUsers(String vroleid);

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users ORDER BY r.vrolename ASC")
  List<MasterRole> findAllWithUsersByOrderByVrolenameAsc();

  @Query("SELECT r FROM MasterRole r WHERE LOWER(r.vrolename) = LOWER(:vrolename)")
  Optional<MasterRole> findByVrolenameIgnoreCase(String vrolename);

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users WHERE LOWER(r.users.vusername) = LOWER(:username)")
  List<MasterRole> findByUsers_VusernameIgnoreCase(String username);

  boolean existsByVrolenameIgnoreCase(String vrolename);

  @Query("SELECT COUNT(r) FROM MasterRole r")
  long countAll();

}