create table users(
    id serial primary key ,
    firstname varchar(255) not null ,
    lastname varchar(255) not null ,
    username varchar(255) unique not null,
    password varchar(255) not null,
    role varchar(20) not null,
    enabled boolean
);

create table todos(
    id serial primary key ,
    task varchar(255) not null,
    status varchar(20) not null,
    start_of_executing timestamp,
    end_of_executing timestamp
);