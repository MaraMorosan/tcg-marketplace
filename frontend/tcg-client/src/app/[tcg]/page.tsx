import { notFound } from "next/navigation";
import Carousel from "../../components/home/Carousel";
import BestSellers from "../../components/home/BestSellers";
import LiveAuctions from "../../components/home/LiveAuctions";
import Expansions from "../../components/home/Expansions";

import styles from "./page.module.scss";

const VALID_TCGS = ["Pokemon", "MagicTheGathering", "YuGiOh", "OnePiece"];

export default async function TcgPage({ params }: { params: { tcg: string } }) {
  const { tcg } = await Promise.resolve(params);

  if (!VALID_TCGS.includes(tcg)) {
    notFound();
  }

  return (
    <div className="container">
      <Carousel />
      <div className={styles.marketplace}>
        <BestSellers />
        <LiveAuctions />
      </div>
      <div className={styles.expansions}>
        <Expansions />
      </div>
    </div>
  );
}