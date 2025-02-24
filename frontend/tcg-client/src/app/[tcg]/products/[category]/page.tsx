"use client";

import ProductList from "@/components/products/product-list/ProductList";

export default function ProductsPage({ params }: { params: { tcg: string; category: string } }) {
  return (
    <ProductList />
  );
}