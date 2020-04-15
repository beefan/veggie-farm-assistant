BEGIN TRANSACTION;

truncate loss cascade;
truncate sales cascade;
truncate harvest cascade;
truncate bed cascade;
truncate field cascade;
truncate users cascade;
truncate seeding_times cascade;
truncate expiration cascade;
truncate crop cascade;


INSERT INTO users ("username", "password", "salt", "role") VALUES
('user',
'FjZDm+sndmsdEDwNtfr6NA==',
'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=',
'user');
insert into field (name, username) values ('Pepper Field', 'user');
insert into crop (crop_name) values ('Peruvian Puff Pepper');
insert into crop (crop_name) values ('Red Bell Pepper');
insert into bed (crop_name, planting_date, transplant_date, field_id) values ('Peruvian Puff Pepper','2020-01-07','2019-12-15', 1);
insert into bed (crop_name, planting_date, transplant_date, field_id) values ('Red Bell Pepper','2020-01-17','2020-01-01', 1);
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 40.00, 'Farmers Market', '2020-04-09');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 17.17, 'Farmers Market', '2020-04-10');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 13.24, 'Farmers Market', '2020-04-11');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 69.73, 'Farmers Market', '2020-04-12');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 40.94, 'Farmers Market', '2020-04-13');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 55.55, 'Farmers Market', '2020-04-14');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 7.68, 'Farmers Market', '2020-04-15');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 35.17, 'Farmers Market', '2020-04-16');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (1, 42.07, 'Farmers Market', '2020-04-17');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 4.7, 'user', '2020-04-09');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 5.2, 'user', '2020-04-10');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 0.3, 'user', '2020-04-11');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 9.8, 'user', '2020-04-12');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 3.3, 'user', '2020-04-13');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 2.9, 'user', '2020-04-14');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 5.5, 'user', '2020-04-15');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 1.7, 'user', '2020-04-16');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (1, 1, 4.7, 'user', '2020-04-17');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 27.55, 'Farmers Market', '2020-04-09');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 32.75, 'Farmers Market', '2020-04-10');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 17.68, 'Farmers Market', '2020-04-11');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 00.00, 'Farmers Market', '2020-04-12');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 29.01, 'Farmers Market', '2020-04-13');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 48.58, 'Farmers Market', '2020-04-14');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 24.12, 'Farmers Market', '2020-04-15');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 13.13, 'Farmers Market', '2020-04-16');
insert into sales (crop_id, dollar_amount, sale_type, sale_date) values (2, 23.76, 'Farmers Market', '2020-04-17');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 8.8, 'user', '2020-04-09');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 11.0, 'user', '2020-04-10');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 14.1, 'user', '2020-04-11');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 3.7, 'user', '2020-04-12');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 7.2, 'user', '2020-04-13');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 8.7, 'user', '2020-04-14');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 7.9, 'user', '2020-04-15');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 6.4, 'user', '2020-04-16');
insert into harvest (bed_id, crop_id, crop_weight, username, harvest_date) values (2, 2, 6.5, 'user', '2020-04-17');


COMMIT TRANSACTION;


 