CREATE TABLE
  master_users (
    vuserid VARCHAR(50) PRIMARY KEY NOT NULL,
    vusername VARCHAR(100) NOT NULL UNIQUE,
    vemail VARCHAR(100) NOT NULL UNIQUE,
    vphonenumber VARCHAR(20) NULL,
    vpassword VARCHAR(100) NOT NULL,
    vfullname VARCHAR(150) NULL,
    created_by VARCHAR(100) NULL,
    created_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP,
      updated_by VARCHAR(100) NULL,
      updated_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP
  );

CREATE INDEX idx_master_users_phone_number ON master_users (vphonenumber);

CREATE INDEX idx_master_users_email ON master_users (vemail);

CREATE INDEX idx_master_users_username ON master_users (vusername);