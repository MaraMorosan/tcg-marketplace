"use client";

import { useState, useEffect, useRef } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { FiChevronLeft, FiChevronRight } from "react-icons/fi";
import { useSwipeable } from "react-swipeable";
import { useRouter, usePathname } from "next/navigation";
import styles from "./carousel.module.scss";

const banners = [
  {
    id: 1,
    title: "Hot Community Posts!",
    subtitle: "Check out discussions!",
    image: "https://theuncommonshop.ch/wp-content/uploads/2024/11/prismatic-evolutions-vorbestellen-Pokemon-in-der-schweiz.jpg",
  },
  {
    id: 2,
    title: "New TCG Set Released!",
    subtitle: "Discover new collections!",
    image: "https://www.pokebox.com.au/cdn/shop/collections/Paldea_Evolved_Banner_2048x_b84b3a42-9343-4da2-8078-8863a81aeb56.webp?v=1679977328",
  },
  {
    id: 3,
    title: "Trade Your Cards!",
    subtitle: "Find great deals!",
    image: "https://asia.pokemon-card.com/my/wp-content/uploads/sites/6/2025/01/my_top_banner_showdown2025.jpg",
  },
];

export default function Carousel() {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [direction, setDirection] = useState(0);
  const autoplayRef = useRef<NodeJS.Timeout | null>(null);

  const router = useRouter();
  const pathname = usePathname() ?? "/";

  const tcg = pathname.split("/")[1];

  const resetAutoplay = () => {
    if (autoplayRef.current) clearTimeout(autoplayRef.current);
    autoplayRef.current = setTimeout(() => {
      setDirection(1);
      setCurrentIndex((prev) => (prev + 1) % banners.length);
    }, 10000);
  };

  useEffect(() => {
    resetAutoplay();
    return () => {
      autoplayRef.current && clearTimeout(autoplayRef.current);
    };
  }, [currentIndex]);

  const nextSlide = () => {
    setDirection(1);
    setCurrentIndex((prev) => (prev + 1) % banners.length);
    resetAutoplay();
  };

  const prevSlide = () => {
    setDirection(-1);
    setCurrentIndex((prev) => (prev === 0 ? banners.length - 1 : prev - 1));
    resetAutoplay();
  };

  const handlers = useSwipeable({
    onSwiping: (eventData) => {
      eventData.event.preventDefault();
    },
    onSwipedLeft: nextSlide,
    onSwipedRight: prevSlide,
    trackMouse: true,
  });

  const variants = {
    enter: (direction: number) => ({
      x: direction > 0 ? 300 : -300,
      opacity: 0,
    }),
    center: {
      x: 0,
      opacity: 1,
      zIndex: 1,
    },
    exit: (direction: number) => ({
      x: direction < 0 ? 300 : -300,
      opacity: 0,
      zIndex: 0,
    }),
  };

  const transition = {
    x: { type: "spring", stiffness: 300, damping: 30 },
    opacity: { duration: 0.2 },
  };

  const handleRedirect = () => {
    if (tcg) {
      router.push(`/${tcg}/community`);
    }
  };

  return (
    <div className={styles.carousel} {...handlers}>
      <AnimatePresence custom={direction} mode="popLayout">
        <motion.div
          key={banners[currentIndex].id}
          className={styles.slide}
          style={{ backgroundImage: `url(${banners[currentIndex].image})` }}
          custom={direction}
          variants={variants}
          initial="enter"
          animate="center"
          exit="exit"
          transition={transition}
        >
          <div className={styles.overlay}>
            <h2>{banners[currentIndex].title}</h2>
            <p>{banners[currentIndex].subtitle}</p>
            <button className={styles.button} onClick={handleRedirect}>
              View More
            </button>
          </div>
        </motion.div>
      </AnimatePresence>

      <button className={styles.prev} onClick={prevSlide}>
        <FiChevronLeft />
      </button>
      <button className={styles.next} onClick={nextSlide}>
        <FiChevronRight />
      </button>

      <div className={styles.dots}>
        {banners.map((_, idx) => (
          <div
            key={idx}
            className={`${styles.dot} ${idx === currentIndex ? styles.active : ""}`}
            onClick={() => {
              setDirection(idx > currentIndex ? 1 : -1);
              setCurrentIndex(idx);
            }}
          />
        ))}
      </div>
    </div>
  );
}
