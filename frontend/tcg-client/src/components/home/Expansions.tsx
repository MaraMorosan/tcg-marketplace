"use client";

import styles from "./expansions.module.scss";
import { useRouter } from "next/navigation";

export default function Expansions() {
  const router = useRouter();

  const handleViewAll = () => {
    const currentPath = window.location.pathname;
    router.push(`${currentPath}/products`);
  };

  return (
    <div className={styles.expansions}>
      <div className={styles.viewAllContainer}>
        <button className={styles.viewAllButton} onClick={handleViewAll}>View Products</button>
      </div>
    </div>
  );
}