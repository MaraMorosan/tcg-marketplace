"use client";

import React, { useState, useEffect } from "react";
import styles from "./best-sellers.module.scss";
import { CARDS_ENDPOINT } from "@/config/api";
import { useRouter, usePathname } from "next/navigation";

const TCG_MAP: Record<string, number> = {
  Pokemon: 1,
  MagicTheGathering: 2,
  YuGiOh: 3,
  OnePiece: 4,
};

export default function BestSellers() {
  const [cards, setCards] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();
  const pathname = usePathname() ?? "/";
  const tcg = pathname.split("/")[1];

  const tcgId = TCG_MAP[tcg];

  useEffect(() => {
    const fetchCards = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await fetch(`${CARDS_ENDPOINT}/tcg/${tcgId}`);
        if (!response.ok) {
          throw new Error("Failed to fetch best sellers.");
        }
        const data = await response.json();
        setCards(data);
      } catch (error: any) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCards();
  }, []);

  const sortedCards = [...cards].sort((a, b) => a.name.localeCompare(b.name));

  return (
    <div className={styles.bestSellersPage}>
      <h1 className={styles.title}>All Best Sellers</h1>

      {loading ? (
        <p className={styles.loading}>Loading...</p>
      ) : error ? (
        <p className={styles.error}>{error}</p>
      ) : (
        <div className={styles.cardsGrid}>
          {sortedCards.length > 0 ? (
            sortedCards.map((card) => (
              <div key={card.id} className={styles.cardItem}>
                <div className={styles.imageWrapper}>
                  <img src={card.imageUrl} alt={card.name} />
                </div>
                <h3 className={styles.cardName}>{card.name}</h3>
                <p className={styles.expansion}>
                  {card.expansionName || "Unknown Expansion"}
                </p>
                <p className={styles.rarity}>
                  {card.rarityName || "Unknown Rarity"}
                </p>
                <p className={styles.price}>
                  {card.price ? `${card.price} EUR` : "Price not available"}
                </p>
              </div>
            ))
          ) : (
            <p className={styles.noResults}>No best-selling cards available.</p>
          )}
        </div>
      )}
    </div>
  );
}
