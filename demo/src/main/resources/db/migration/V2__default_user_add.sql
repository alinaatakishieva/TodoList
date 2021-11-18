insert into users(firstname, lastname, username, password, enabled)
values('Alina', 'Atakishieva', 'alina', crypt('123456', gen_salt('bf', 8)), true);