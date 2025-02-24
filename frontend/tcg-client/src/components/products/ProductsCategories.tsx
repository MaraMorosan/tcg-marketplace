"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import styles from "./products-categories.module.scss";
import { PRODUCTS_CATEGORIES_ENDPOINT } from "@/config/api";

interface Category {
  name: string;
  path: string;
  image: string;
}

export default function ProductCategories() {
  const router = useRouter();
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await fetch(PRODUCTS_CATEGORIES_ENDPOINT);
        if (!res.ok) {
          throw new Error("Failed to fetch categories");
        }
        const data = await res.json();
        console.log("Categories received:", data);

        const uniqueCategories = new Map<string, Category>();

        data.forEach((product: any) => {
          const rawCategoryName = product.productType; 

          if (!uniqueCategories.has(rawCategoryName)) {
            uniqueCategories.set(rawCategoryName, {
              name: rawCategoryName,
              path: rawCategoryName.toLowerCase(),
              image: product.imageUrl || "https://tcg.pokemon.com/assets/img/global/tcg-card-back-2x.jpg"
            });
          }
        });

        setCategories(Array.from(uniqueCategories.values()));
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  const handleRedirectToCategory = (categoryPath: string) => {
    if (typeof window !== "undefined") {
      const currentPath = window.location.pathname;
      router.push(`${currentPath}/${categoryPath}`);
    }
  };

  if (loading) return <p>Loading categories...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className={styles.categoriesContainer}>
      {categories.map((category) => (
        <div
          key={category.path}
          className={styles.categoryCard}
          onClick={() => handleRedirectToCategory(category.path)}
        >
          <img
            src={category.image}
            alt={category.name}
            width={200}
            height={150}
            className={styles.categoryImage}
          />
          <h2 className={styles.categoryName}>{category.name}</h2>
        </div>
      ))}
    </div>
  );
}