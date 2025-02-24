"use client";

import ProductList from "@/components/products/product-list/ProductList";

export default function ExpansionPage({ params }: { params: { tcg: string; category: string; expansion: string } }) {
    return (
      <ProductList />
    );
  }