package com.tcgmarketplace.expansion;

import com.tcgmarketplace.card.Card;
import com.tcgmarketplace.tcg.Tcg;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "expansion")
public class Expansion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expansion_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tcg_id", nullable = false)
    private Tcg tcg;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String code;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
