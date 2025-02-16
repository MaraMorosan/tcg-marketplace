package com.tcgmarketplace.rarity;

import com.tcgmarketplace.tcg.rarity.TcgRarity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rarity")
public class Rarity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rarity_id")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "rarity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TcgRarity> tcgRarities;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TcgRarity> getTcgRarities() {
        return tcgRarities;
    }

    public void setTcgRarities(List<TcgRarity> tcgRarities) {
        this.tcgRarities = tcgRarities;
    }
}

