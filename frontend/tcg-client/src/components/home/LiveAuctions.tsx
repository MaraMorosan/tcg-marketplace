"use client";

import { useState, useEffect } from "react";
import styles from "./live-auctions.module.scss";

const auctions = [
  { id: 1, name: "Rare Black Lotus", price: "€4500", timeLeft: 120, image: null },
  { id: 2, name: "Charizard 1st Edition", price: "€2000", timeLeft: 90, image: null },
  { id: 3, name: "Blue-Eyes White Dragon", price: "€750", timeLeft: 60, image: null },
  { id: 4, name: "Pikachu Illustrator", price: "€10000", timeLeft: 30, image: null },
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
            <img src={auction.image} alt={auction.name} />
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