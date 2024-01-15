create table auto_post
(
    id            serial primary key,
    description   varchar not null,
    creation      timestamp,
    auto_user_id  int references auto_user(id)
);