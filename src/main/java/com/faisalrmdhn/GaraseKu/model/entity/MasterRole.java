package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.faisalrmdhn.GaraseKu.util.Ulid;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_roles", indexes = {
    @Index(name = "idx_master_roles_name", columnList = "vrolename")
})
public class MasterRole extends DefaultEntity {
  @Id
  @Ulid
  @Column(name = "vroleid", nullable = false, length = 50)
  private String vroleid;

  @Column(name = "vrolename", nullable = false, length = 100)
  private String vrolename;

  @Column(name = "vroledesc", nullable = true)
  private String vroledesc;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  }, mappedBy = "roles")
  private Set<MasterUser> users;

  public MasterRole() {
  }

  public MasterRole(String vroleid, String vrolename, String vroledesc, Set<MasterUser> users) {
    this.vroleid = vroleid;
    this.vrolename = vrolename;
    this.vroledesc = vroledesc;
    this.users = users;
  }

  public MasterRole(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
      String vroleid, String vrolename, String vroledesc, Set<MasterUser> users) {
    super(createdBy, createdAt, updatedBy, updatedAt);
    this.vroleid = vroleid;
    this.vrolename = vrolename;
    this.vroledesc = vroledesc;
    this.users = users;
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

  public Set<MasterUser> getUsers() {
    return users;
  }

  public void setUsers(Set<MasterUser> users) {
    this.users = users;
  }

}
