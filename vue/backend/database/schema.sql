BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user')
);
create table crop (
  id serial primary key,
  crop_name varchar(64) NOT NULL
);
create table seeding_times (
  id serial primary key,
  crop_id int not null,
  direct_seed_to_harvest int,
  direct_seed_to_transplant int,
  transplant_to_harvest int,
  constraint fk_crop_id foreign key (crop_id) references crop (id)
);

COMMIT TRANSACTION;
