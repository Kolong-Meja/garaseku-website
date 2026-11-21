create table "master_user_settings" (
  "vusersettingid" varchar(50) primary key not null,
  "vuserid" varchar(50) not null,
  "vsettingkey" varchar(100) not null,
  "vsettingvalue" varchar(1000) not null,
  "created_at" timestamp with time zone default current_timestamp,
  "created_by" varchar(100) null,
  "updated_at" timestamp with time zone default current_timestamp,
  "updated_by" varchar(100) null,
  constraint "uk_master_user_settings_user_key" unique ("vuserid", "vsettingkey")
);

create unique index "idx_master_user_settings_user" on "master_user_settings" ("vuserid");

create unique index "idx_master_user_settings_key" on "master_user_settings" ("vsettingkey");