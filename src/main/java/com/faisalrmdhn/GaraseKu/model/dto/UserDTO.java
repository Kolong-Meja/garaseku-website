package com.faisalrmdhn.GaraseKu.model.dto;

import java.util.List;

import com.faisalrmdhn.GaraseKu.model.entity.MasterUser;

public record UserDTO(String id,
    String username,
    String email,
    String phoneNumber,
    String fullname,
    List<RoleDTO> roles) {
  public UserDTO(String id,
      String username,
      String email,
      String phoneNumber,
      String fullname,
      List<RoleDTO> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.fullname = fullname;
    this.roles = roles;
  }

  public static final UserDTO fromObject(MasterUser masterUser) {
    return new UserDTO(masterUser.getVuserid(), masterUser.getVusername(), masterUser.getVemail(),
        masterUser.getVphonenumber(), masterUser.getVfullname(),
        masterUser.getRoles().stream().map(RoleDTO::fromObject).toList());
  }

  public static final UserDTO fromObjectWithoutRoles(MasterUser masterUser) {
    return new UserDTO(masterUser.getVuserid(), masterUser.getVusername(), masterUser.getVemail(),
        masterUser.getVphonenumber(), masterUser.getVfullname(), null);
  }
}
