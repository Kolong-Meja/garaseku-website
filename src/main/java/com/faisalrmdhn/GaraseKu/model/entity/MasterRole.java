package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;

import com.faisalrmdhn.GaraseKu.util.Ulid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_roles")
public class MasterRole extends DefaultEntity {
  @Id
  @Ulid
  @Column(name = "vroleid", nullable = false, length = 50)
  private String vroleid;

  @Column(name = "vrolename", nullable = false, length = 100)
  private String vrolename;

  @Column(name = "vroledesc", nullable = true)
  private String vroledesc;

  public MasterRole() {
  }

  public MasterRole(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
      String vroleid, String vrolename, String vroledesc) {
    super(createdBy, createdAt, updatedBy, updatedAt);
    this.vroleid = vroleid;
    this.vrolename = vrolename;
    this.vroledesc = vroledesc;
  }

  public MasterRole(String vroleid, String vrolename, String vroledesc) {
    this.vroleid = vroleid;
    this.vrolename = vrolename;
    this.vroledesc = vroledesc;
  }

  public String getVroleid() {
    return vroleid;
  }

  public void setVroleid(String vroleid) {
    this.vroleid = vroleid;
  }

  public String getVrolename() {
    return vrolename;
  }

  public void setVrolename(String vrolename) {
    this.vrolename = vrolename;
  }

  public String getVroledesc() {
    return vroledesc;
  }

  public void setVroledesc(String vroledesc) {
    this.vroledesc = vroledesc;
  }

}
