package com.tcgmarketplace.tcg;

import com.tcgmarketplace.expansion.Expansion;
import com.tcgmarketplace.tcg.rarity.TcgRarity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tcg")
public class Tcg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tcg_id")
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "tcg", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expansion> expansions;

    @OneToMany(mappedBy = "tcg", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TcgRarity> rarities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
