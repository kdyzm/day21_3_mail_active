create database active character set utf8;
use active;
create table users(
 id varchar(32) primary key,
 name varchar(30),
 pwd  varchar(32),
 email varchar(50)
);
create table active(
  uid varchar(32) primary key,
  code varchar(64),
  constraint a_fk foreign key(uid) references users(id)
);
