"use client"; // important pentru a putea folosi router-ul și state-ul

import { useState, useEffect } from "react";
import { useRouter, usePathname } from "next/navigation";
import Link from "next/link";
import { FiSearch, FiShoppingCart, FiUser } from "react-icons/fi";
import styles from "./header.module.scss";
import AuthModal from "../forms/AuthModal";

const TCG_VALUES = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default function Header() {
  const router = useRouter();
  const pathname = usePathname();

  const currentTcgFromUrl = TCG_VALUES.find((val) => pathname?.includes(val));

  const [selectedTCG, setSelectedTCG] = useState(currentTcgFromUrl || "Pokemon");

  const currentTcg = currentTcgFromUrl || "Pokemon";

  const handleTCGChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newTcg = e.target.value;
    setSelectedTCG(newTcg);
    router.push(`/${newTcg}`);
  };

  const [showAuthModal, setShowAuthModal] = useState(false);

  const handleProfileClick = () => {
    setShowAuthModal(true);
  };
  const handleCloseModal = () => {
    setShowAuthModal(false);
  };

  const handleCartClick = () => {
    router.push(`/${currentTcg}/cart`);
  };

  useEffect(() => {
    if (currentTcgFromUrl && currentTcgFromUrl !== selectedTCG) {
      setSelectedTCG(currentTcgFromUrl);
    }
  }, [currentTcgFromUrl, selectedTCG]);

  return (
    <>
      <header className={styles.header}>
        <div className={styles.container}>
          <Link href="/" className={styles.logo}>
            TCG Trade Shop
          </Link>

          <div className={styles.search}>
            <FiSearch className={styles.icon} />
            <input type="text" placeholder="Search for a product..." />
          </div>

          <div className={styles.rightSection}>
            <div className={styles.tcgSwitcher}>
              <select value={selectedTCG} onChange={handleTCGChange}>
                {TCG_VALUES.map((tcgOption) => (
                  <option key={tcgOption} value={tcgOption}>
                    {tcgOption}
                  </option>
                ))}
              </select>
            </div>

            <div className={styles.profile} onClick={handleProfileClick}>
              <FiUser />
            </div>

            <div className={styles.profile} onClick={handleCartClick}>
              <FiShoppingCart />
            </div>
          </div>
        </div>
      </header>

    <AuthModal show={showAuthModal} onClose={handleCloseModal} />
    </>
  );
}