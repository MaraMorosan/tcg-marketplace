"use client";

import styles from "./login-form.module.scss";

interface LoginFormProps {
  onToggleMode: () => void;
  onLogin: (username: string, password: string) => void;
}

export default function LoginForm({ onToggleMode, onLogin }: LoginFormProps) {
  return (
    <div className={styles.formContent}>
      <h2>Login</h2>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          const formData = new FormData(e.currentTarget);
          const username = formData.get("username") as string;
          const password = formData.get("password") as string;
          onLogin(username, password);
        }}
      >
        <label>
          Username:
          <input type="text" name="username" placeholder="Enter your username" required />
        </label>
        <label>
          Password:
          <input type="password" name="password" placeholder="Enter your password" required />
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