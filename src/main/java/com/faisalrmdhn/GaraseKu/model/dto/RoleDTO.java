package com.faisalrmdhn.GaraseKu.model.dto;

import java.util.List;

import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;

public record RoleDTO(String id, String name, String desc, List<UserDTO> users) {
  public RoleDTO(String id, String name, String desc, List<UserDTO> users) {
    this.id = id;
    this.name = name;
    this.desc = desc;
    this.users = users;
  }

  public static final RoleDTO fromObject(MasterRole masterRole) {
    return new RoleDTO(masterRole.getVroleid(), masterRole.getVrolename(), masterRole.getVroledesc(),
        masterRole.getUsers().stream().map(UserDTO::fromObjectWithoutRoles).toList());
  }
}
