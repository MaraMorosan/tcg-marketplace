package com.tcgmarketplace.tcg;

import com.tcgmarketplace.rarity.Rarity;
import jakarta.persistence.*;

@Entity
@Table(name = "tcg_rarity")
public class TcgRarity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tcg_rarity_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tcg_id", nullable = false)
    private Tcg tcg;

    @ManyToOne
    @JoinColumn(name = "rarity_id", nullable = false)
    private Rarity rarity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tcg getTcg() {
        return tcg;
    }

    public void setTcg(Tcg tcg) {
        this.tcg = tcg;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
