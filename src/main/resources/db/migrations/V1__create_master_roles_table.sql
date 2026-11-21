CREATE TABLE
  master_roles (
    vroleid VARCHAR(50) PRIMARY KEY NOT NULL,
    vrolename VARCHAR(100) NOT NULL UNIQUE,
    vroledesc TEXT NULL,
    created_by VARCHAR(100) NULL,
    created_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP,
      updated_by VARCHAR(100) NULL,
      updated_at TIMESTAMP
    WITH
      TIME ZONE DEFAULT CURRENT_TIMESTAMP
  );