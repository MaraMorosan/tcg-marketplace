"use client";

import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import styles from "./product.module.scss";
import { PRODUCTS_ENDPOINT } from "@/config/api";

export default function Product() {
  const params = useParams() as { tcg?: string; category?: string; expansion?: string; cardName?: string };
  const { category, expansion, cardName } = params;
  
  const [product, setProduct] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProductDetail = async () => {
      try {
        const endpoint = `${PRODUCTS_ENDPOINT}/${category?.toLowerCase()}/${expansion}/${cardName}`;
        console.log("Fetching:", endpoint);
        const response = await fetch(endpoint);
        if (!response.ok) {
          throw new Error("Failed to fetch product details");
        }
        const data = await response.json();
        setProduct(data);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    if (category && expansion && cardName) {
      fetchProductDetail();
    } else {
      setLoading(false);
    }
  }, [category, expansion, cardName]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!product) return <div>No product found</div>;

  return (
    <div className={styles.productContainer}>
      <h1>{product.name}</h1>
      <img src={product.cardImageUrl} alt={product.name} />
      <p><strong>Product Type:</strong> {product.productType}</p>
      <p><strong>Expansion:</strong> {product.expansionName}</p>
    </div>
  );
}