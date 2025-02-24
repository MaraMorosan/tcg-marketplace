"use client";

import { useState, useEffect } from "react";
import styles from "./live-auctions.module.scss";

const auctions = [
  { id: 1, name: "Umbreon Gold Star", price: "€4500", timeLeft: 120, image: "https://federicstore.it/wp-content/uploads/2021/10/040-025gs-granfesta.png" },
  { id: 2, name: "Charizard EX", price: "€2000", timeLeft: 90, image: "https://federicstore.it/wp-content/uploads/2020/06/sv49-sv94gx-destinosfuggente.png" },
  { id: 3, name: "Sylveon VMAX", price: "€750", timeLeft: 60, image: "https://federicstore.it/wp-content/uploads/2022/02/TG14-TG30v-astrilucenti.png" },
  { id: 4, name: "Pikachu EX", price: "€10000", timeLeft: 30, image: "https://federicstore.it/wp-content/uploads/2020/08/184-181gxa-gioco.png" },
];

export default function LiveAuctions() {
  const [auctionData, setAuctionData] = useState(auctions);

  useEffect(() => {
    const interval = setInterval(() => {
      setAuctionData((prevAuctions) =>
        prevAuctions.map((auction) =>
          auction.timeLeft > 0 ? { ...auction, timeLeft: auction.timeLeft - 1 } : auction
        )
      );
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className={styles.liveAuctions}>
      <h2>Live Auctions</h2>
      <ul>
        {auctionData.map((auction) => (
          <li key={auction.id} className={styles.auctionItem}>
            <img 
              src={auction.image ?? "/placeholder.jpg"} 
              alt={auction.name} 
            />
            <div>
              <h3>{auction.name}</h3>
              <p>Current Price: {auction.price}</p>
              <p>Time Left: {auction.timeLeft}s</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}