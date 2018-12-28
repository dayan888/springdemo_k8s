echo "create database demodb;" | psql -U postgres

echo "create table users (id integer primary key, login character varying(16) not null, password character varying(16) not null);"| psql -U postgres demodb
echo "create unique index users_ux1 on users(login);" | psql -U postgres demodb
echo "create table todos (id integer primary key, title character varying(16) not null, status integer default 0 not null, dt timestamp default now() not null);" | psql -U postgres demodb
echo "create sequence todo_id_seq;" | psql -U postgres demodb
echo "insert into users values(0, 'admin', '111111');" | psql -U postgres demodb
