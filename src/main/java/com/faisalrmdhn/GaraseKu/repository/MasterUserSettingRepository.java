package com.faisalrmdhn.GaraseKu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faisalrmdhn.GaraseKu.model.entity.MasterUserSetting;

public interface MasterUserSettingRepository extends JpaRepository<MasterUserSetting, String> {

  List<MasterUserSetting> findAllByMasterUserMasterUserPkVuseridOrderByVsettingkeyAsc(String vuserid);

  Optional<MasterUserSetting> findByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid,
      String vsettingkey);

  boolean existsByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid, String vsettingkey);

  long deleteByMasterUserMasterUserPkVuseridAndVsettingkey(String vuserid, String vsettingkey);

  long deleteAllByMasterUserMasterUserPkVuserid(String vuserid);
}
