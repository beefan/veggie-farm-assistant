BEGIN TRANSACTION;

drop table if exists loss;
drop table if exists sales;
drop table if exists harvest;
drop table if exists bed;
drop table if exists field;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS seeding_times;
DROP TABLE IF EXISTS expiration;
DROP TABLE IF EXISTS crop;



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

create table expiration(
 id serial primary key,
 crop_id int not null,
 days_until_expire int not null,
 constraint fk_crop_id foreign key (crop_id) references crop (id)
 );
 
 create table field (
 id serial primary key,
 name varchar(64) not null unique,
 username varchar(255) NOT NULL,
 constraint fk_username foreign key (username) references users (username)
 );
 
 create table bed (
 id serial primary key,
 field_id int not null,
 crop_name varchar(64) not null,
 planting_date date not null,
 transplant_date date,
 constraint fk_field_id foreign key (field_id) references field (id)
 );
 
 create table harvest(
 id serial primary key,
 bed_id int not null,
 crop_id int not null,
 crop_weight decimal not null,
 crop_count int,
 username varchar(255) not null,
 harvest_date date not null,
 constraint fk_bed_id foreign key (bed_id) references bed(id),
 constraint fk_crop_id foreign key (crop_id) references crop(id),
 constraint fk_username foreign key (username) references users(username)
 );
 
 create table sales(
 id serial primary key,
 crop_id int not null,
 dollar_amount decimal not null,
 sale_type varchar(255) not null,
 sale_date date not null,
constraint fk_crop_name foreign key (crop_id) references crop(id)
);

create Table loss(
id serial primary key,
crop_id int not null,
weight_info numeric not null,
loss_type varchar(64) not null,
dollar_amount decimal,
constraint fk_crop_name foreign key (crop_id) references crop(id)
); 



COMMIT TRANSACTION;
