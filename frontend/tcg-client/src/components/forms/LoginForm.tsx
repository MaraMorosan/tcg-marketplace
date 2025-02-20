"use client";

import styles from "./login-form.module.scss";

interface LoginFormProps {
  onToggleMode: () => void;
}

export default function LoginForm({ onToggleMode }: LoginFormProps) {
  return (
    <div className={styles.formContent}>
      <h2>Login</h2>
      <form>
        <label>
          Email:
          <input type="email" placeholder="Enter your email" />
        </label>
        <label>
          Password:
          <input type="password" placeholder="Enter your password" />
        </label>
        <button type="submit" className={styles.submitBtn}>Login</button>
      </form>

      <button className={styles.googleBtn}>
        Sign in with Google
      </button>

      <p className={styles.switchText}>
        Do not have an account?{" "}
        <button 
          type="button" 
          onClick={onToggleMode} 
          className={styles.switchBtn}
        >
          Register
        </button>
      </p>
    </div>
  );
}