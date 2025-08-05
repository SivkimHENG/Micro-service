"use client";

import { useState } from "react";
import { RegisterAction, RegisterResult } from "./action";

export default function RegisterPage() {
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [submitting, setSubmitting] = useState(false);

  async function onSubmit(formData: FormData) {
    setSubmitting(true);
    const result: RegisterResult = await RegisterAction(formData);
    setSubmitting(false);

    if (!result.success) {
      console.log("Server returned errors:", result.errors);
      setErrors(result.errors);
    } else {
      // Redirect on success
      window.location.href = "/login";
    }
  }

  return (
    <form action={onSubmit}>

      <div>
        <label>
          Username
          <input name="username" type="text" placeholder="yourname" />
        </label>
        {errors.username && <p className="text-red-600">{errors.username}</p>}
      </div>
      <div>
        <label>
          email
          <input name="email" type="email" placeholder="you@example.com" />
        </label>
        {errors.email && <p className="text-red-600">{errors.email}</p>}
      </div>

      <div>
        <label>
          Password
          <input name="password" type="password" placeholder="••••••••••••" />
        </label>
        {errors.password && <p className="text-red-600">{errors.password}</p>}
      </div>

      <div>
        <label>
          Confirm Password
          <input
            name="confirmPassword"
            type="password"
            placeholder="Repeat password"
          />
        </label>
        {errors.confirmPassword && (
          <p className="text-red-600">{errors.confirmPassword}</p>
        )}
      </div>

      <button type="submit" disabled={submitting}>
        {submitting ? "Registering…" : "Register"}
      </button>
    </form>
  );
}


