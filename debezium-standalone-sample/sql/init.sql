-- create database "test";
\c "test";
CREATE SCHEMA IF NOT EXISTS "test";
CREATE TABLE init_table (
    id int,
    metainf varchar
);
insert into init_table values (1,'Initial record');