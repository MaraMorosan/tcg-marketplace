package com.tcgmarketplace.card;

import com.tcgmarketplace.expansion.Expansion;
import com.tcgmarketplace.tcg.rarity.TcgRarity;
import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "expansion_id", nullable = false)
    private Expansion expansion;

    @ManyToOne
    @JoinColumn(name = "tcg_rarity_id", nullable = false)
    private TcgRarity tcgRarity;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "collector_number", length = 50)
    private String collectorNumber;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public TcgRarity getTcgRarity() {
        return tcgRarity;
    }

    public void setTcgRarity(TcgRarity tcgRarity) {
        this.tcgRarity = tcgRarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
