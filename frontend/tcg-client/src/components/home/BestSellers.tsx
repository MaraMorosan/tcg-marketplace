"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import styles from "./best-sellers.module.scss";
import { CARDS_ENDPOINT } from "@/config/api";
import { useRouter, usePathname } from "next/navigation";

const TCG_MAP: Record<string, number> = {
  Pokemon: 1,
  MagicTheGathering: 2,
  YuGiOh: 3,
  OnePiece: 4,
};

interface Card {
  id: number;
  name: string;
  imageUrl: string;
  tcgRarityId: number;
}

export default function BestSellers() {
  const router = useRouter();
  const pathname = usePathname() ?? "/";
  const tcg = pathname.split("/")[1];

  const tcgId = TCG_MAP[tcg];

  const [cards, setCards] = useState<Card[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const handleViewAll = () => {
    if (typeof window !== "undefined") {
      const currentPath = window.location.pathname;
      router.push(`${currentPath}/best-sellers`);
    }
  };

  useEffect(() => {
    if (!tcgId) return;

    const url = `${CARDS_ENDPOINT}/tcg/${tcgId}`;

    axios
      .get(url)
      .then((response) => {
        setCards(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching cards:", err);
        setError("Error loading data.");
        setLoading(false);
      });
  }, [tcgId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  const sortedCards = [...cards].sort((a, b) => a.name.localeCompare(b.name));
  const topThree = sortedCards.slice(0, 3);
  const others = sortedCards.slice(3, 10);

  return (
    <div className={styles.bestSellers}>
      <h2>Best Sellers in {tcg}</h2>

      <div className={styles.topThreeContainer}>
        {topThree.map((card, index) => (
          <div key={card.id} className={styles.topProduct}>
            <div className={styles.imageWrapper}>
              {card.imageUrl && <img src={card.imageUrl} alt={card.name} />}
              <div className={styles.badge}>{index + 1}</div>
            </div>
            <h3>{card.name}</h3>
            <p className={styles.soldInfo}>0 sold in the last 24h</p>
          </div>
        ))}
      </div>

      <ol className={styles.otherProductsList}>
        {others.map((card, index) => (
          <li key={card.id} className={styles.otherProduct}>
            <span className={styles.position}>{index + 4}.</span>
            <h3>{card.name}</h3>
            <p className={styles.soldInfo}>0 sold in the last 24h</p>
          </li>
        ))}
      </ol>

      <div className={styles.viewAllContainer}>
        <button className={styles.viewAllButton} onClick={handleViewAll}>
          View All
        </button>
      </div>
    </div>
  );
}
