create table files(
    id serial primary key,
    name varchar unique,
    extension varchar not null,
    size bigint,
    todo_id integer references todos (id)
);