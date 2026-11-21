create table "master_users" (
  "vuserid" varchar(50) primary key not null,
  "vusername" varchar(100) unique not null,
  "vemail" varchar(100) unique not null,
  "vphonenumber" varchar(20) unique null,
  "vpassword" varchar(100) not null,
  "vfullname" varchar(150) null,
  "created_at" timestamp with time zone default current_timestamp,
  "created_by" varchar(100) null,
  "updated_at" timestamp with time zone default current_timestamp,
  "updated_by" varchar(100) null,
);