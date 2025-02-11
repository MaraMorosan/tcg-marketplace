package com.tcgmarketplace.tcg;

import com.tcgmarketplace.product.ProductCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "tcg_category")
public class TcgCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tcg_id", nullable = false)
    private Tcg tcg;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;

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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
