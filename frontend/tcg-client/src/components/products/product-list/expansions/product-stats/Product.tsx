"use client";

import { useSearchParams } from "next/navigation";

export default function Product() {
  const searchParams = useSearchParams();

  const id = searchParams!.get("id");
  const image = searchParams!.get("image");
  const price = searchParams!.get("price");
  const category = searchParams!.get("category");
  const expansion = searchParams!.get("expansion");
  const rarity = searchParams!.get("rarity");

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Product Details</h2>

      {image && <img src={image} alt="product-img" width={200} />}
      
      <p>ID: {id}</p>
      <p>Price: {price} EUR</p>
      <p>Category: {category}</p>
      <p>Expansion: {expansion}</p>
      <p>Rarity: {rarity}</p>
    </div>
  );
}