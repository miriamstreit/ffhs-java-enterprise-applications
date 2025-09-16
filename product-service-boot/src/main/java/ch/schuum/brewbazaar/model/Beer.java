package ch.schuum.brewbazaar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="beer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @Column(name = "beer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int beerId;

    private String name;

    private double price;

    private String imageUrl;

    private int stock;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<IngredientAmount> ingredients;
}
