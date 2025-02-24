"use client";

import ProductCategories from "@/components/products/ProductsCategories";
import styles from "./page.module.scss";

export default function ProductsPage() {
  return (
    <div className={styles.productsWrapper}>
      <h1>Choose a Product Type</h1>
      <ProductCategories />
    </div>
  );
}
