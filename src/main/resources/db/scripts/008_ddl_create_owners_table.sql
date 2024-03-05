create table owners
(
    id            serial primary key,
    name          varchar not null,
    user_id       int references auto_user(id),
    history_id    int references history(id)
);