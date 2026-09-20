package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_users", indexes = {
    @Index(name = "idx_master_users_phone_number", columnList = "vphonenumber")
})
public class MasterUser extends DefaultEntity implements UserDetails {
  @EmbeddedId
  private MasterUserPk masterUserPk;

  @Column(name = "vusername", nullable = false, unique = true, length = 100)
  private String vusername;

  @Column(name = "vemail", nullable = false, unique = true, length = 100)
  private String vemail;

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
  @JoinTable(name = "master_user_roles", joinColumns = @JoinColumn(name = "vuserid", referencedColumnName = "vuserid", nullable = false), inverseJoinColumns = @JoinColumn(name = "vroleid", referencedColumnName = "vroleid", nullable = false))
  private Set<MasterRole> roles = new LinkedHashSet<>();

  @OneToMany(mappedBy = "masterUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MasterUserSetting> settings = new LinkedHashSet<>();

  public MasterUser() {
  }

  public MasterUser(MasterUserPk masterUserPk, String vusername, String vemail, String vphonenumber,
      String vpassword, String vfullname, Set<MasterRole> roles) {
    this.masterUserPk = masterUserPk;
    this.vusername = vusername;
    this.vemail = vemail;
    this.vphonenumber = vphonenumber;
    this.vpassword = vpassword;
    this.vfullname = vfullname;
    setRoles(roles);
  }

  public MasterUser(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
      MasterUserPk masterUserPk, String vusername, String vemail, String vphonenumber, String vpassword,
      String vfullname, Set<MasterRole> roles) {
    super(createdBy, createdAt, updatedBy, updatedAt);
    this.masterUserPk = masterUserPk;
    this.vusername = vusername;
    this.vemail = vemail;
    this.vphonenumber = vphonenumber;
    this.vpassword = vpassword;
    this.vfullname = vfullname;
    setRoles(roles);
  }

  public MasterUserPk getMasterUserPk() {
    return masterUserPk;
  }

  public void setMasterUserPk(MasterUserPk masterUserPk) {
    this.masterUserPk = masterUserPk;
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
    this.roles = roles == null ? new LinkedHashSet<>() : roles;
  }

  public Set<MasterUserSetting> getSettings() {
    return settings;
  }

  public void setSettings(Set<MasterUserSetting> settings) {
    this.settings.clear();
    if (settings != null) {
      settings.forEach(this::addSetting);
    }
  }

  public void addSetting(MasterUserSetting setting) {
    settings.add(setting);
    setting.setMasterUser(this);
  }

  public void removeSetting(MasterUserSetting setting) {
    settings.remove(setting);
    setting.setMasterUser(null);
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
    return vemail;
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
