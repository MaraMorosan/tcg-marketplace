"use client";

import { useState, useEffect, useRef } from "react";
import { useRouter, usePathname } from "next/navigation";
import Link from "next/link";
import { FiSearch, FiShoppingCart, FiUser } from "react-icons/fi";
import styles from "./header.module.scss";
import AuthModal from "../forms/AuthModal";
import { PRODUCTS_ENDPOINT } from "@/config/api";

const TCG_VALUES = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default function Header() {
  const router = useRouter();
  const pathname = usePathname();

  const currentTcgFromUrl = TCG_VALUES.find((val) => pathname?.includes(val));
  const [selectedTCG, setSelectedTCG] = useState(currentTcgFromUrl || "Pokemon");

  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState<any[]>([]);
  const [totalHits, setTotalHits] = useState(0);
  const [showDropdown, setShowDropdown] = useState(false);

  const [showAuthModal, setShowAuthModal] = useState(false);
  const handleProfileClick = () => setShowAuthModal(true);
  const handleCloseModal = () => setShowAuthModal(false);

  const handleTCGChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newTcg = e.target.value;
    setSelectedTCG(newTcg);
    router.push(`/${newTcg}`);
  };

  const handleCartClick = () => {
    router.push(`/${selectedTCG}/cart`);
  };

  useEffect(() => {
    if (searchTerm.length < 2) {
      setSearchResults([]);
      setTotalHits(0);
      setShowDropdown(false);
      return;
    }

    const timeoutId = setTimeout(() => {
      fetchProducts(searchTerm);
    }, 300);

    return () => clearTimeout(timeoutId);
  }, [searchTerm]);

  async function fetchProducts(term: string) {
    try {
      const resp = await fetch(`${PRODUCTS_ENDPOINT}?name=${term}&limit=10`);
      if (!resp.ok) {
        throw new Error("Failed to fetch products");
      }
      const data = await resp.json();

      setSearchResults(data.results || []);
      setTotalHits(data.total || 0);
      setShowDropdown(true);
    } catch (error) {
      console.error("Search error:", error);
    }
  }

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  const handleShowAll = () => {
    router.push(`/search?term=${searchTerm}`);
    setShowDropdown(false);
    setSearchTerm("");
  };

  const searchRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    function handleClickOutside(e: MouseEvent) {
      if (searchRef.current && !searchRef.current.contains(e.target as Node)) {
        setShowDropdown(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <>
      <header className={styles.header}>
        <div className={styles.container}>
          <Link href="/" className={styles.logo}>
            TCG Trade Shop
          </Link>

          <div className={styles.search} ref={searchRef}>
            <FiSearch className={styles.icon} />
            <input
              type="text"
              placeholder="Search for a product..."
              value={searchTerm}
              onChange={handleSearchChange}
              onFocus={() => {
                if (searchResults.length > 0) setShowDropdown(true);
              }}
            />

            {showDropdown && searchResults.length > 0 && (
              <div className={styles.dropdown}>
                {searchResults.map((product, idx) => (
                  <div key={idx} className={styles.dropdownItem}>
                    {product.name}
                  </div>
                ))}

                {totalHits > 10 && (
                  <div
                    className={styles.showAll}
                    onClick={handleShowAll}
                  >
                    Show all ({totalHits} hits)
                  </div>
                )}
              </div>
            )}
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
