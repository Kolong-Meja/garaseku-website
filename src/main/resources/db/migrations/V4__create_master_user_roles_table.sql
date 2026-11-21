create table "master_user_roles" (
  "vuserid" varchar(50) not null,
  "vroleid" varchar(50) not null,
  primary key ("vuserid", "vroleid"),
  constraint "fk_master_user_roles_user" foreign key ("vuserid") references "master_users" ("vuserid") on delete cascade,
  constraint "fk_master_user_roles_role" foreign key ("vroleid") references "master_roles" ("vroleid") on delete cascade
);