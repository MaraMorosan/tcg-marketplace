import styles from "./page.module.scss";

export default async function CommunityPage({ params }: { params: { tcg: string } }) {
  const { tcg } = await params;

  return (
    <div className={styles.communityContainer}>
      <h1 className={styles.communityTitle}>Welcome to the {tcg} Community</h1>
      <p className={styles.communityText}>
        Here you can discuss and trade your {tcg} cards with other players!
      </p>
    </div>
  );
}
