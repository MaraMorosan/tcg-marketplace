"use client";

import { useState } from "react";
import Cookies from "js-cookie";
import styles from "./register-form.module.scss";
import { REGISTER_ENDPOINT } from "@/config/api";

interface RegisterFormProps {
  onToggleMode: () => void;
}

export default function RegisterForm({ onToggleMode }: RegisterFormProps) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSuccessMessage("");
  
    try {
      const response = await fetch(REGISTER_ENDPOINT, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username: name, email, password }),
      });
  
      if (!response.ok) {
        throw new Error("Registration failed");
      }
  
      setSuccessMessage("Account created successfully! You can now log in.");
      setTimeout(() => {
        onToggleMode();
      }, 1500);
    } catch (error) {
      setError("Failed to register. Please try again.");
    }
  };
  
  

  return (
    <div className={styles.formContent}>
      <h2>Register</h2>
      {error && <p className={styles.error}>{error}</p>}
      {successMessage && <p className={styles.success}>{successMessage}</p>}
      <form onSubmit={handleRegister}>
        <label>
          Name:
          <input type="text" placeholder="Enter your name" value={name} onChange={(e) => setName(e.target.value)} required />
        </label>
        <label>
          Email:
          <input type="email" placeholder="Enter your email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </label>
        <label>
          Password:
          <input type="password" placeholder="Enter your password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </label>
        <button type="submit" className={styles.submitBtn}>Create Account</button>
      </form>

      <p className={styles.switchText}>
        Already have an account?{" "}
        <button 
          type="button" 
          onClick={onToggleMode} 
          className={styles.switchBtn}
        >
          Login
        </button>
      </p>
    </div>
  );
}
