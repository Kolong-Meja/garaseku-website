package com.faisalrmdhn.GaraseKu.model.entity;

import java.io.Serializable;

import com.faisalrmdhn.GaraseKu.util.Ulid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class MasterUserPk implements Serializable {

  @Id
  @Ulid
  @Column(name = "vuserid", nullable = false, length = 50)
  private String vuserid;

  @Column(name = "vusername", nullable = false, length = 100)
  private String vusername;

  @Column(name = "vemail", nullable = false, length = 100)
  private String vemail;

  public MasterUserPk() {
  }

  public MasterUserPk(String vuserid, String vusername, String vemail) {
    this.vuserid = vuserid;
    this.vusername = vusername;
    this.vemail = vemail;
  }

  public String getVuserid() {
    return vuserid;
  }

  public void setVuserid(String vuserid) {
    this.vuserid = vuserid;
  }

  public String getVusername() {
    return vusername;
  }

  public void setVusername(String vusername) {
    this.vusername = vusername;
  }

  public String getVemail() {
    return vemail;
  }

  public void setVemail(String vemail) {
    this.vemail = vemail;
  }

}
