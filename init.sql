-- create database "test";
\c "test";
CREATE SCHEMA IF NOT EXISTS "test";
CREATE TABLE test.init_table (
    id int,
    metainf varchar
);
insert into test.init_table values (1,'Initial record');