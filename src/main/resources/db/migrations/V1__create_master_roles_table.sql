create table "master_roles" (
  "vroleid" varchar(50) primary key not null,
  "vrolename" varchar(100) unique not null,
  "vroledesc" text null,
  "created_at" timestamp with time zone default current_timestamp,
  "created_by" varchar(100) null,
  "updated_at" timestamp with time zone default current_timestamp,
  "updated_by" varchar(100) null
);

create unique index "idx_master_roles_name" on "master_roles" ("vrolename");