import "../styles/Globals.scss";
import Header from "../components/layout/Header";
import Footer from "../components/layout/Footer";
import styles from "./layout.module.scss";

export const metadata = {
  title: "TCG Trade Shop",
  description: "SEO Description testing",
  icons: {
    icon: "/PikachuIconTcg.ico",
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ro">
      <body className={styles.layout}>
        <Header />
        <main className={styles.main}>
          {children}
        </main>
        <Footer />
      </body>
    </html>
  );
}