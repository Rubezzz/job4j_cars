package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @OneToMany(mappedBy = "car")
    private Set<HistoryOwner> historyOwners;
}