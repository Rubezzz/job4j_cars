create table file
(
    id   serial primary key,
    name varchar not null,
    path varchar not null unique,
    post_id      int references auto_post(id)
);