import { notFound, redirect } from "next/navigation";
import { cookies } from "next/headers";

const VALID_TCGS = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default async function CartPage({ params }: { params: { tcg: string } }) {
  const { tcg } = await params;

  if (!VALID_TCGS.includes(tcg)) {
    notFound();
  }

  const token = (await cookies()).get("token")?.value;

  if (!token) {
    redirect(`/${tcg}`);
  }

  return (
    <div style={{ padding: 20 }}>
      <h1>{tcg} Cart Page</h1>
    </div>
  );
}
