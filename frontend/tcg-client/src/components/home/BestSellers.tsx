"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import styles from "./best-sellers.module.scss";

interface Card {
  id: number;
  name: string;
  imageUrl: string;
  price?: string;
  soldIn24h?: number;
}

export default function BestSellers() {
  const [cards, setCards] = useState<Card[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const url = `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/card`;
    axios
      .get(url)
      .then((response) => {
        setCards(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Eroare la preluarea datelor:", err);
        setError(err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Se încarcă...</p>;
  if (error) return <p>Eroare la încărcarea datelor.</p>;

  const sortedCards = [...cards].sort((a, b) => a.name.localeCompare(b.name));
  const topThree = sortedCards.slice(0, 3);
  const others = sortedCards.slice(3);

  return (
    <div className={styles.bestSellers}>
      <h2>Best Sellers</h2>

      <div className={styles.topThreeContainer}>
        {topThree.map((card, index) => (
          <div key={card.id} className={styles.topProduct}>
            <div className={styles.imageWrapper}>
              {card.imageUrl && <img src={card.imageUrl} alt={card.name} />}
              <div className={styles.badge}>{index + 1}</div>
            </div>
            <h3>{card.name}</h3>
            {/* <p className={styles.price}>{card.price}</p> */}
            <p className={styles.soldInfo}>12 sold in the last 24h</p>
          </div>
        ))}
      </div>

      <ol className={styles.otherProductsList}>
        {others.map((card, index) => (
          <li key={card.id} className={styles.otherProduct}>
            <span className={styles.position}>{index + 4}.</span>
            <h3>{card.name}</h3>
            {/* <p className={styles.price}>{card.price}</p> */}
            <p className={styles.soldInfo}>12 sold in the last 24h</p>
          </li>
        ))}
      </ol>
    </div>
  );
}
