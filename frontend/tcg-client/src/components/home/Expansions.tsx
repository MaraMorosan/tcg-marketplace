"use client";

import { useState } from "react";
import styles from "./expansions.module.scss";

const expansions = [
  {
    id: 1,
    name: "Prismatic Evolutions",
    image: "https://tcg.pokemon.com/assets/img/sv-expansions/prismatic-evolutions/logo/en-us/sv8pt5-logo.png",
    cards: [
      { id: 101, name: "Umbreon EX", price: "€49.99", image: null },
      { id: 102, name: "Espeon EX", price: "€39.99", image: null },
      { id: 103, name: "Glaceon VSTAR", price: "€59.99", image: null },
    ],
  },
  {
    id: 2,
    name: "Surging Sparks",
    image: "https://cardboardwarriors.net/assets/surging-sparks.png",
    cards: [
      { id: 201, name: "Raikou GX", price: "€35.99", image: null },
      { id: 202, name: "Entei V", price: "€45.99", image: null },
      { id: 203, name: "Suicune EX", price: "€55.99", image: null },
    ],
  },
  {
    id: 3,
    name: "Stellar Crown",
    image: "https://redpandagaming.com.au/cdn/shop/collections/stellar_crown_category.png?v=1725853099",
    cards: [
      { id: 301, name: "Darkrai VMAX", price: "€65.99", image: null },
      { id: 302, name: "Tyranitar GX", price: "€50.99", image: null },
      { id: 303, name: "Gengar EX", price: "€40.99", image: null },
    ],
  },
];

export default function Expansions() {
  const [selectedExpansion, setSelectedExpansion] = useState(expansions[0]);

  return (
    <div className={styles.expansions}>
      <h2>Latest Expansions</h2>

      <div className={styles.expansionList}>
        {expansions.map((expansion) => (
          <div
            key={expansion.id}
            className={`${styles.expansionItem} ${selectedExpansion.id === expansion.id ? styles.active : ""}`}
            onClick={() => setSelectedExpansion(expansion)}
          >
            <img src={expansion.image} alt={expansion.name} />
            <p>{expansion.name}</p>
          </div>
        ))}
      </div>

      <div className={styles.cardsList}>
        <h3>Cards from {selectedExpansion.name}</h3>
        <ul>
          {selectedExpansion.cards.map((card) => (
            <li key={card.id} className={styles.cardItem}>
              <img src={card.image} alt={card.name} />
              <div>
                <h4>{card.name}</h4>
                <p>{card.price}</p>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}