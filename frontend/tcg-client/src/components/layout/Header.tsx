"use client";

import { useState, useEffect, useRef } from "react";
import { useRouter, usePathname } from "next/navigation";
import Link from "next/link";
import { FiSearch, FiShoppingCart, FiUser } from "react-icons/fi";
import styles from "./header.module.scss";
import AuthModal from "../forms/AuthModal";
import { AUTH_LOGIN_ENDPOINT, LOGGED_USER, SEARCH_PRODUCTS_ENDPOINT } from "@/config/api";
import Cookies from "js-cookie";

const TCG_VALUES = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default function Header() {
  const router = useRouter();
  const pathname = usePathname();

  const currentTcgFromUrl = TCG_VALUES.find((val) => pathname?.includes(val));
  const [selectedTCG, setSelectedTCG] = useState(currentTcgFromUrl || "Pokemon");

  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState<any[]>([]);
  const [totalHits, setTotalHits] = useState(0);
  const [showSearchDropdown, setShowSearchDropdown] = useState(false);
  const [showUserDropdown, setShowUserDropdown] = useState(false);
  const [token, setToken] = useState<string | null>(Cookies.get("token") || null);
  const [user, setUser] = useState<{ username: string} | null>(null);
  const [isClient, setIsClient] = useState(false);
  
  useEffect(() => {
    const tokenFromCookie = Cookies.get("token");
    setIsClient(true);
    if (tokenFromCookie) {
      setToken(tokenFromCookie);
  
      fetch(LOGGED_USER, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${tokenFromCookie}`,
          "Content-Type": "application/json"
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch user data");
        }
        return response.json();
      })
      .then(data => {
        setUser({
          username: data.username
        });
      })
      .catch(error => {
        console.error("Error fetching user data:", error);
        setToken(null);
        Cookies.remove("token");
      });
    }
  }, []);

  const [showAuthModal, setShowAuthModal] = useState(false);

  const handleTCGChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newTcg = e.target.value;
    setSelectedTCG(newTcg);
    router.push(`/${newTcg}`);
  };

  const handleProfileClick = () => {
    if (user) {
      setShowUserDropdown((prev) => !prev);
      setShowSearchDropdown(false);
    } else {
      setShowAuthModal(true);
    }
  };
  

  const handleCloseModal = () => {
    setShowAuthModal(false);
  };

  const handleCartClick = () => {
    if (user) {
      router.push(`/${selectedTCG}/cart`);
    }
  };

  useEffect(() => {
    if (currentTcgFromUrl && currentTcgFromUrl !== selectedTCG) {
      setSelectedTCG(currentTcgFromUrl);
    }
  }, [currentTcgFromUrl, selectedTCG]);

  useEffect(() => {
    if (searchTerm.length < 2) {
      setSearchResults([]);
      setTotalHits(0);
      setShowSearchDropdown(false);
      return;
    }

    const timeoutId = setTimeout(() => {
      fetchProducts(searchTerm);
    }, 300);

    return () => clearTimeout(timeoutId);
  }, [searchTerm]);

  const handleLogin = async (username: string, password: string) => {
    try {
      const response = await fetch(AUTH_LOGIN_ENDPOINT, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
  
      if (!response.ok) {
        throw new Error("Login failed");
      }
  
      const data = await response.json();
      const { token } = data;
  
      Cookies.set("token", token, { expires: 7, secure: true });
  
      setToken(token);
  
      fetch(LOGGED_USER, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      })
      .then(response => response.json())
      .then(userData => {
        setUser({
          username: userData.username
        });
      });
  
      setShowAuthModal(false);
    } catch (error) {
      console.error("Login error:", error);
      alert("Invalid username or password");
    }
  };

  const handleRegisterSuccess = async (token: string) => {
    Cookies.set("token", token, { expires: 7, secure: true });
    setToken(token);
  
    try {
      const response = await fetch(LOGGED_USER, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
  
      const text = await response.text();
      if (!text) {
        throw new Error("Empty response from server");
      }
  
      const userData = JSON.parse(text);
  
      if (userData) {
        setUser({ username: userData.username });
      }
  
      setShowAuthModal(false);
    } catch (error) {
      console.error("Error fetching user data:", error);
    }
  };

  const handleLogout = () => {
    Cookies.remove("token");
    setToken(null);
    setUser(null);
    setShowUserDropdown(false);
  
    window.location.reload();
  };
  

  async function fetchProducts(term: string) {
    try {
      const resp = await fetch(`${SEARCH_PRODUCTS_ENDPOINT}?name=${term}&limit=10`);
      if (!resp.ok) {
        throw new Error("Failed to fetch products");
      }
      const data = await resp.json();

      setSearchResults(data.results || []);
      setTotalHits(data.total || 0);
      setShowSearchDropdown(true);
    } catch (error) {
      console.error("Search error:", error);
    }
  }

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
    if (e.target.value.length > 1) {
      setShowSearchDropdown(true);
      setShowUserDropdown(false);
    } else {
      setShowSearchDropdown(false);
    }
  };

  const handleShowAll = () => {
    router.push(`/search?term=${searchTerm}`);
    setShowSearchDropdown(false);
    setSearchTerm("");
  };

  const searchRef = useRef<HTMLDivElement>(null);
  const userRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(e: MouseEvent) {
      if (searchRef.current && !searchRef.current.contains(e.target as Node)) {
        setShowSearchDropdown(false);
      }
      if (userRef.current && !userRef.current.contains(e.target as Node)) {
        setShowUserDropdown(false);
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
                if (searchResults.length > 0) setShowSearchDropdown(true);
              }}
            />

            {showSearchDropdown && searchResults.length > 0 && (
              <div className={styles.dropdownSearch}>
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

            {isClient && token ? (
              <div className={styles.userSection} ref={userRef}>
                <span className={styles.username} onClick={handleProfileClick}>
                  {user?.username}
                </span>
                {showUserDropdown && (
                  <div className={styles.dropdownUser}>
                    <Link href="/account">Account</Link>
                    <button onClick={handleLogout}>Logout</button>
                  </div>
                )}
                <div className={styles.profile} onClick={handleCartClick}>
                  <FiShoppingCart />
                </div>
              </div>
            ) : (
              <div className={styles.profile} onClick={() => setShowAuthModal(true)}>
                <FiUser />
              </div>
            )}
          </div>
        </div>
      </header>

      <AuthModal show={showAuthModal} onClose={handleCloseModal} onLogin={handleLogin} onRegisterSuccess={handleRegisterSuccess}/>
    </>
  );
}
