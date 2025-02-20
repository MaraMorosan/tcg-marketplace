"use client";

import styles from "./best-sellers.module.scss";

const bestSellers = [
  {
    id: 1,
    name: "Charizard VMAX",
    price: "€199.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 25,
  },
  {
    id: 2,
    name: "Pikachu Illustrator",
    price: "€999.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 20,
  },
  {
    id: 3,
    name: "Blue-Eyes White Dragon",
    price: "€349.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 18,
  },
  {
    id: 4,
    name: "Black Lotus",
    price: "€5000.00",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 15,
  },
  {
    id: 5,
    name: "Dark Magician",
    price: "€129.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 14,
  },
  {
    id: 6,
    name: "Exodia Set",
    price: "€999.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 13,
  },
  {
    id: 7,
    name: "Mox Emerald",
    price: "€1200.00",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 12,
  },
  {
    id: 8,
    name: "Time Walk",
    price: "€2500.00",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 10,
  },
  {
    id: 9,
    name: "Shining Charizard",
    price: "€499.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 9,
  },
  {
    id: 10,
    name: "Raigeki",
    price: "€79.99",
    image: "https://dz3we2x72f7ol.cloudfront.net/expansions/prismatic-evolutions/en-us/SV8pt5_EN_18.png",
    soldIn24h: 8,
  },
];

export default function BestSellers() {
  const sortedBestSellers = [...bestSellers].sort(
    (a, b) => b.soldIn24h - a.soldIn24h
  );

  const topThree = sortedBestSellers.slice(0, 3);
  const others = sortedBestSellers.slice(3);

  return (
    <div className={styles.bestSellers}>
      <h2>Best Sellers</h2>

      <div className={styles.topThreeContainer}>
        {topThree.map((product, index) => (
          <div key={product.id} className={styles.topProduct}>
            <div className={styles.imageWrapper}>
              {product.image && <img src={product.image} alt={product.name} />}
              <div className={styles.badge}>{index + 1}</div>
            </div>
            <h3>{product.name}</h3>
            <p className={styles.price}>{product.price}</p>
            <p className={styles.soldInfo}>
              {product.soldIn24h} sold in the last 24h
            </p>
          </div>
        ))}
      </div>

      <ol className={styles.otherProductsList}>
        {others.map((product, index) => (
          <li key={product.id} className={styles.otherProduct}>
            <span className={styles.position}>{index + 4}.</span>
            <h3>{product.name}</h3>
            <p className={styles.price}>{product.price}</p>
            <p className={styles.soldInfo}>
              {product.soldIn24h} sold in the last 24h
            </p>
          </li>
        ))}
      </ol>
    </div>
  );
}