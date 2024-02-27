package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICE_HISTORY")
@Data
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int before;
    private int after;
    private LocalDateTime created;
}
