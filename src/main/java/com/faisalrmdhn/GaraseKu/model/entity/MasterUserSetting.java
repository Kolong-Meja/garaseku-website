package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;

import com.faisalrmdhn.GaraseKu.util.Ulid;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "master_user_settings", uniqueConstraints = {
    @UniqueConstraint(name = "uk_master_user_settings_user_key", columnNames = { "vuserid", "vsettingkey" })
}, indexes = {
    @Index(name = "idx_master_user_settings_user", columnList = "vuserid"),
    @Index(name = "idx_master_user_settings_key", columnList = "vsettingkey")
})
public class MasterUserSetting extends DefaultEntity {
  @Id
  @Ulid
  @Column(name = "vusersettingid", nullable = false, length = 50)
  private String vusersettingid;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "vuserid", referencedColumnName = "vuserid", nullable = false)
  private MasterUser masterUser;

  @Column(name = "vsettingkey", nullable = false, length = 100)
  private String vsettingkey;

  @Column(name = "vsettingvalue", nullable = false, length = 1000)
  private String vsettingvalue;

  public MasterUserSetting() {
  }

  public MasterUserSetting(String vusersettingid, MasterUser masterUser, String vsettingkey, String vsettingvalue) {
    this.vusersettingid = vusersettingid;
    this.masterUser = masterUser;
    this.vsettingkey = vsettingkey;
    this.vsettingvalue = vsettingvalue;
  }

  public MasterUserSetting(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
      String vusersettingid, MasterUser masterUser, String vsettingkey, String vsettingvalue) {
    super(createdBy, createdAt, updatedBy, updatedAt);
    this.vusersettingid = vusersettingid;
    this.masterUser = masterUser;
    this.vsettingkey = vsettingkey;
    this.vsettingvalue = vsettingvalue;
  }

  public String getVusersettingid() {
    return vusersettingid;
  }

  public void setVusersettingid(String vusersettingid) {
    this.vusersettingid = vusersettingid;
  }

  public MasterUser getMasterUser() {
    return masterUser;
  }

  public void setMasterUser(MasterUser masterUser) {
    this.masterUser = masterUser;
  }

  public String getVsettingkey() {
    return vsettingkey;
  }

  public void setVsettingkey(String vsettingkey) {
    this.vsettingkey = vsettingkey;
  }

  public String getVsettingvalue() {
    return vsettingvalue;
  }

  public void setVsettingvalue(String vsettingvalue) {
    this.vsettingvalue = vsettingvalue;
  }
}
