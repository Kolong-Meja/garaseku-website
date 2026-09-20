package com.faisalrmdhn.GaraseKu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faisalrmdhn.GaraseKu.model.entity.MasterUser;
import com.faisalrmdhn.GaraseKu.model.entity.MasterUserPk;

public interface MasterUserRepository extends JpaRepository<MasterUser, MasterUserPk> {

  @Query("SELECT u FROM MasterUser u WHERE u.masterUserPk.vuserid = :vuserid")
  Optional<MasterUser> findByUserId(@Param("vuserid") String vuserid);

  @EntityGraph(attributePaths = "settings")
  @Query("SELECT u FROM MasterUser u WHERE u.masterUserPk.vuserid = :vuserid")
  Optional<MasterUser> findByUserIdWithSettings(@Param("vuserid") String vuserid);

  @EntityGraph(attributePaths = "roles")
  @Query("SELECT u FROM MasterUser u WHERE u.masterUserPk.vuserid = :vuserid")
  Optional<MasterUser> findByUserIdWithRoles(@Param("vuserid") String vuserid);

  @Query("SELECT u FROM MasterUser u WHERE u.vusername = :vusername")
  Optional<MasterUser> findByUsername(@Param("vusername") String vusername);

  @Query("SELECT u FROM MasterUser u WHERE u.vemail = :vemail")
  Optional<MasterUser> findByEmail(@Param("vemail") String vemail);

  @Query("SELECT DISTINCT u FROM MasterUser u JOIN u.roles r WHERE LOWER(r.vrolename) = LOWER(:role)")
  List<MasterUser> findByRole(@Param("role") String role);

  @Query("SELECT u FROM MasterUser u WHERE LOWER(u.vfullname) LIKE LOWER(CONCAT('%', :vfullname, '%'))")
  List<MasterUser> findByFullname(@Param("vfullname") String vfullname);

  boolean existsByVusernameIgnoreCase(String vusername);

  boolean existsByVemailIgnoreCase(String vemail);
}
