create table history_owners
(
    id            serial primary key,
    car_id        int references car(id),
    owner_id      int references owners(id)
);