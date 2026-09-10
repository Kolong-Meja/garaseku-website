package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_users", indexes = {
    @Index(name = "idx_master_users_username", columnList = "vusername"),
    @Index(name = "idx_master_users_email", columnList = "vemail"),
    @Index(name = "idx_master_users_phone_number", columnList = "vphonenumber")
})
public class MasterUser extends DefaultEntity implements UserDetails {
  @EmbeddedId
  private MasterUserPk masterUserPk;

  @Column(name = "vphonenumber", nullable = true, length = 20)
  private String vphonenumber;

  @Column(name = "vpassword", nullable = true, length = 100)
  private String vpassword;

  @Column(name = "vfullname", nullable = true, length = 150)
  private String vfullname;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "master_user_roles", joinColumns = @JoinColumn(name = "vuserid", nullable = false), inverseJoinColumns = @JoinColumn(name = "vroleid", nullable = false))
  private Set<MasterRole> roles;

  public MasterUser() {
  }

  public MasterUser(MasterUserPk masterUserPk, String vphonenumber, String vpassword, String vfullname,
      Set<MasterRole> roles) {
    this.masterUserPk = masterUserPk;
    this.vphonenumber = vphonenumber;
    this.vpassword = vpassword;
    this.vfullname = vfullname;
    this.roles = roles;
  }

  public MasterUser(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
      MasterUserPk masterUserPk, String vphonenumber, String vpassword, String vfullname, Set<MasterRole> roles) {
    super(createdBy, createdAt, updatedBy, updatedAt);
    this.masterUserPk = masterUserPk;
    this.vphonenumber = vphonenumber;
    this.vpassword = vpassword;
    this.vfullname = vfullname;
    this.roles = roles;
  }

  public MasterUserPk getMasterUserPk() {
    return masterUserPk;
  }

  public void setMasterUserPk(MasterUserPk masterUserPk) {
    this.masterUserPk = masterUserPk;
  }

  public String getVphonenumber() {
    return vphonenumber;
  }

  public void setVphonenumber(String vphonenumber) {
    this.vphonenumber = vphonenumber;
  }

  public String getVpassword() {
    return vpassword;
  }

  public void setVpassword(String vpassword) {
    this.vpassword = vpassword;
  }

  public String getVfullname() {
    return vfullname;
  }

  public void setVfullname(String vfullname) {
    this.vfullname = vfullname;
  }

  public Set<MasterRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<MasterRole> roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getVrolename().toUpperCase()))
        .collect(Collectors.toSet());
  }

  @Override
  public @Nullable String getPassword() {
    return vpassword;
  }

  @Override
  public String getUsername() {
    return masterUserPk.getVemail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
