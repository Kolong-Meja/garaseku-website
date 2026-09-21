package com.faisalrmdhn.GaraseKu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faisalrmdhn.GaraseKu.model.entity.MasterUserSetting;

public interface MasterUserSettingRepository extends JpaRepository<MasterUserSetting, String> {

  @Query("SELECT us FROM MasterUserSetting us WHERE us.masterUser.masterUserPk.vuserid = :vuserid ORDER BY us.vsettingkey ASC")
  List<MasterUserSetting> findAllByMasterUserMasterUserPkVuseridOrderByVsettingkeyAsc(String vuserid);

  @Query("SELECT us FROM MasterUserSetting us WHERE us.masterUser.masterUserPk.vuserid = :vuserid AND us.vsettingkey = :vsettingkey")
  Optional<MasterUserSetting> findByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid, String vsettingkey);

  @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END FROM MasterUserSetting us WHERE us.masterUser.masterUserPk.vuserid = :vuserid AND us.vsettingkey = :vsettingkey")
  boolean existsByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid, String vsettingkey);

  long deleteByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid, String vsettingkey);

  long deleteAllByMasterUserMasterUserPkVuserid(String vuserid);
}
