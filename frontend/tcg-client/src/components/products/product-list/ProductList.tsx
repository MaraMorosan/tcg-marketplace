"use client";

import { useState, useEffect } from "react";
import { useRouter, useParams } from "next/navigation";
import { PRODUCTS_FILTER_ENDPOINT } from "@/config/api";
import styles from "./product-list.module.scss";

export default function ProductList() {
  const router = useRouter();
  const params = useParams() as { tcg?: string; category?: string; expansion?: string };

  const initialTcg = params.tcg || "Pokemon";     
  const initialCategory = params.category || "All";
  const initialExpansion = params.expansion || "All";

  const [categoryFilter, setCategoryFilter] = useState(initialCategory);
  const [expansionFilter, setExpansionFilter] = useState(initialExpansion);
  const [rarityFilter, setRarityFilter] = useState("All");
  const [sortBy, setSortBy] = useState("nameAsc");
  const [searchName, setSearchName] = useState("");

  const [displayedProducts, setDisplayedProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function fetchFilteredProducts() {
    setLoading(true);
    setError(null);
    try {
      const url = new URL(PRODUCTS_FILTER_ENDPOINT);
      url.searchParams.append("productType", categoryFilter || "All");
      url.searchParams.append("expansionName", expansionFilter || "All");
      url.searchParams.append("rarityName", rarityFilter || "All");
      url.searchParams.append("searchTerm", searchName.trim());
      url.searchParams.append("sortBy", sortBy);

      const resp = await fetch(url.toString());
      if (!resp.ok) {
        throw new Error("Failed to fetch filtered products");
      }
      const data = await resp.json();
      setDisplayedProducts(data || []);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchFilteredProducts();
  }, [params.category, params.expansion]);

  const handleApplyFilter = () => {
    let newUrl = `/${initialTcg}/products/${categoryFilter}`;

    if (expansionFilter !== "All") {
      newUrl += `/${expansionFilter}`;
    }

    router.push(newUrl);
  };

  return (
    <div className={styles.categoryPage}>
      <div className={styles.filters}>
        <label className={styles.filterLabel}>
          Category:
          <select
            className={styles.selectInput}
            value={categoryFilter}
            onChange={(e) => setCategoryFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="single">Single</option>
            <option value="booster">Booster</option>
            <option value="booster_box">Booster Box</option>
            <option value="sealed">Sealed</option>
          </select>
        </label>

        <label className={styles.filterLabel}>
          Expansion:
          <select
            className={styles.selectInput}
            value={expansionFilter}
            onChange={(e) => setExpansionFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="prismatic-evolutions">Prismatic Evolutions</option>
            <option value="metal-raiders">Metal Raiders</option>
            <option value="pharaohs-servant">Pharaoh's Servant</option>
          </select>
        </label>

        <label className={styles.filterLabel}>
          Rarity:
          <select
            className={styles.selectInput}
            value={rarityFilter}
            onChange={(e) => setRarityFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Common">Common</option>
            <option value="Super Rare">Super Rare</option>
          </select>
        </label>

        <label className={styles.filterLabel}>
          Sort by:
          <select
            className={styles.selectInput}
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
          >
            <option value="nameAsc">Name Asc</option>
            <option value="nameDesc">Name Desc</option>
          </select>
        </label>

        <label className={styles.filterLabel}>
          Search:
          <input
            className={styles.searchInput}
            type="text"
            value={searchName}
            onChange={(e) => setSearchName(e.target.value)}
          />
        </label>

        <button className={styles.searchButton} onClick={handleApplyFilter}>
          Filter
        </button>
      </div>

      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p className={styles.errorMessage}>{error}</p>
      ) : (
        <div className={styles.productGrid}>
          {displayedProducts.length > 0 ? (
            displayedProducts.map((prod, index) => (
              <div key={index} className={styles.productCard}>
                <div className="imageWrapper">
                  <img src={prod.imageUrl} alt={prod.name} />
                </div>
                <h3 className="productName">{prod.name}</h3>
              </div>
            ))
          ) : (
            <p>No products found.</p>
          )}
        </div>
      )}
    </div>
  );
}