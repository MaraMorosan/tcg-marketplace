import { notFound } from "next/navigation";

const VALID_TCGS = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default function CartPage({ params }: { params: { tcg: string } }) {
  const { tcg } = params;

  if (!VALID_TCGS.includes(tcg)) {
    notFound();
  }

  return (
    <div style={{ padding: 20 }}>
      <h1>{tcg} Cart Page</h1>
    </div>
  );
}