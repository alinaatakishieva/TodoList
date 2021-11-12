CREATE TABLE users (
    id serial primary key,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    username varchar(255) unique not null,
    password varchar(255) not null,
    role varchar(20) not null
);

CREATE TABLE todos (
    id serial primary key,
    task varchar(255) not null,
    status varchar(255) not null,
    user_id integer references users (id),
    start_of_execution timestamp,
    end_of_execution timestamp
);