CREATE TABLE
  master_user_settings (
    vusersettingid VARCHAR(50) PRIMARY KEY NOT NULL,
    vuserid VARCHAR(50) NOT NULL,
    vsettingkey VARCHAR(100) NOT NULL,
    vsettingvalue VARCHAR(1000) NOT NULL,
    created_by VARCHAR(100) NULL,
    created_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP,
      updated_by VARCHAR(100) NULL,
      updated_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT uk_master_user_settings_user_key UNIQUE (vuserid, vsettingkey),
      CONSTRAINT fk_master_user_settings_user FOREIGN KEY (vuserid) REFERENCES master_users (vuserid) ON DELETE CASCADE
  );

CREATE INDEX idx_master_user_settings_user ON master_user_settings (vuserid);

CREATE INDEX idx_master_user_settings_key ON master_user_settings (vsettingkey);