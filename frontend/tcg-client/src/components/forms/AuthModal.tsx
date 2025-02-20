"use client";

import { useState } from "react";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";
import styles from "./auth-modal.module.scss";

interface AuthModalProps {
  show: boolean;
  onClose: () => void;
}

export default function AuthModal({ show, onClose }: AuthModalProps) {
  const [authMode, setAuthMode] = useState<"login" | "register">("login");

  if (!show) return null;

  const toggleAuthMode = () => {
    setAuthMode(authMode === "login" ? "register" : "login");
  };

  return (
    <div className={styles.authOverlay}>
      <div className={styles.authModal}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        
        {authMode === "login" ? (
          <LoginForm onToggleMode={toggleAuthMode} />
        ) : (
          <RegisterForm onToggleMode={toggleAuthMode} />
        )}
      </div>
    </div>
  );
}