create table users(
    id serial primary key,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    enabled boolean not null
);

create table todos(
    id serial primary key,
    task varchar(255) not null,
    status varchar(255) not null,
    iser_id integer references users (id)
);

create table roles(
    id serial primary key,
    name varchar(255) unique not null
);
