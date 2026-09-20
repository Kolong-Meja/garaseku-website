package com.faisalrmdhn.GaraseKu.model.entity;

import java.io.Serializable;
import java.util.Objects;

import com.faisalrmdhn.GaraseKu.util.Ulid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MasterUserPk implements Serializable {

  @Ulid
  @Column(name = "vuserid", nullable = false, length = 50)
  private String vuserid;

  public MasterUserPk() {
  }

  public MasterUserPk(String vuserid) {
    this.vuserid = vuserid;
  }

  public String getVuserid() {
    return vuserid;
  }

  public void setVuserid(String vuserid) {
    this.vuserid = vuserid;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof MasterUserPk that)) {
      return false;
    }
    return Objects.equals(vuserid, that.vuserid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vuserid);
  }

}
