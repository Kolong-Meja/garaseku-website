package com.faisalrmdhn.GaraseKu.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;

public interface MasterRoleRepository extends JpaRepository<MasterRole, String> {
  @Query(value = """
      SELECT r
      FROM MasterRole r
      WHERE
          LOWER(r.vrolename) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(COALESCE(r.vroledesc, '')) LIKE LOWER(CONCAT('%', :query, '%'))
          OR EXISTS (
              SELECT u.vuserid
              FROM MasterUser u
              JOIN u.roles ur
              WHERE ur = r
              AND (
                  LOWER(u.vusername) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(u.vemail) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(COALESCE(u.vfullname, '')) LIKE LOWER(CONCAT('%', :query, '%'))
              )
          )
      """, countQuery = """
      SELECT COUNT(r)
      FROM MasterRole r
      WHERE
          LOWER(r.vrolename) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(COALESCE(r.vroledesc, '')) LIKE LOWER(CONCAT('%', :query, '%'))
          OR EXISTS (
              SELECT u.vuserid
              FROM MasterUser u
              JOIN u.roles ur
              WHERE ur = r
              AND (
                  LOWER(u.vusername) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(u.vemail) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(COALESCE(u.vfullname, '')) LIKE LOWER(CONCAT('%', :query, '%'))
              )
          )
      """)
  Page<MasterRole> searchRoles(
      @Param("query") String query,
      Pageable pageable);

  @Query("""
          SELECT DISTINCT r
          FROM MasterRole r
          LEFT JOIN FETCH r.users
          WHERE r.vroleid IN :roleIds
      """)
  List<MasterRole> findAllWithUsersByVroleidIn(
      @Param("roleIds") Collection<String> roleIds);

  @Query("SELECT r FROM MasterRole r WHERE r.vroleid = :vroleid")
  Optional<MasterRole> findByVroleid(String vroleid);

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users WHERE r.vroleid = :vroleid")
  Optional<MasterRole> findByIdWithUsers(String vroleid);

  @Query("SELECT r FROM MasterRole r LEFT JOIN FETCH r.users ORDER BY r.vrolename ASC")
  List<MasterRole> findAllWithUsersByOrderByVrolenameAsc();

  @Query("SELECT r FROM MasterRole r WHERE LOWER(r.vrolename) = LOWER(:vrolename)")
  Optional<MasterRole> findByVrolenameIgnoreCase(String vrolename);

  @Query("""
          SELECT DISTINCT r
          FROM MasterRole r
          JOIN r.users u
          WHERE LOWER(u.vusername) = LOWER(:username)
      """)
  List<MasterRole> findByUsers_VusernameIgnoreCase(
      @Param("username") String username);

  boolean existsByVrolenameIgnoreCase(String vrolename);

  @Query("SELECT COUNT(r) FROM MasterRole r")
  long countAll();

}