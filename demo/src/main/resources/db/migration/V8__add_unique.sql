alter table users
add constraint username_unique unique(username);

alter table roles
add constraint name_unique unique(name);

ALTER TABLE users
ADD CONSTRAINT firstname_lastname_unique UNIQUE (firstname, lastname);