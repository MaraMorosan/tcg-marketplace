"use client";

import styles from "./register-form.module.scss";

interface RegisterFormProps {
  onToggleMode: () => void;
}

export default function RegisterForm({ onToggleMode }: RegisterFormProps) {
  return (
    <div className={styles.formContent}>
      <h2>Register</h2>
      <form>
        <label>
          Name:
          <input type="text" placeholder="Enter your name" />
        </label>
        <label>
          Email:
          <input type="email" placeholder="Enter your email" />
        </label>
        <label>
          Password:
          <input type="password" placeholder="Enter your password" />
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