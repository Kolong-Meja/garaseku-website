CREATE TABLE
  master_user_roles (
    vuserid VARCHAR(50) NOT NULL,
    vroleid VARCHAR(50) NOT NULL,
    PRIMARY KEY (vuserid, vroleid),
    CONSTRAINT fk_master_user_roles_user FOREIGN KEY (vuserid) REFERENCES master_users (vuserid) ON DELETE CASCADE,
    CONSTRAINT fk_master_user_roles_role FOREIGN KEY (vroleid) REFERENCES master_roles (vroleid) ON DELETE CASCADE
  );

CREATE INDEX idx_master_user_roles_role ON master_user_roles (vroleid);