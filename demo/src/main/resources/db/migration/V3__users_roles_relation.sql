create table users_roles(
    user_id integer references users (id),
    role_id integer references roles (id)
);