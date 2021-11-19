create table roles_permissions(
    role_id integer references roles (id),
    permission_id integer references permissions (id)
);