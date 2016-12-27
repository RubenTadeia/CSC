USE ist175268;

SET FOREIGN_KEY_CHECKS = 0; /* Disable foreign key checking.*/
drop table if exists child;
drop table if exists parent;
drop table if exists controls;
SET FOREIGN_KEY_CHECKS = 1; /* Enable foreign key checking.*/


create table IF NOT EXISTS child(
	childNumber integer NOT NULL, /* Numero de Telefone da criança */
    name varchar(30) NOT NULL,
    primary key(childNumber)
    );


create table IF NOT EXISTS parent(
	parentNumber integer NOT NULL, /* Numero de Telefone do responsavel */
    name varchar(30) NOT NULL,
    primary key(parentNumber)
    );


create table IF NOT EXISTS controls(
	childNumber integer NOT NULL,
   	parentNumber integer NOT NULL,
    primary key(childNumber, parentNumber),
    foreign key(childNumber) references child(childNumber),
    foreign key(parentNumber) references parent(parentNumber)
    );