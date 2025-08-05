"use server"

import api from "@/lib/api";
import { AxiosError } from "axios";
import { z } from "zod";
import { cookies } from "next/headers";


const loginSchema = z.object({
  username: z.string().min(1, "Username is required").trim(),
  password: z.string().min(1, "Password is required")
});

export type LoginResult =
  | { success: true; accessToken: string; refreshToken: string; username: string }
  | { success: false; errors: Record<string, string> };

export async function LoginAction(formData: FormData): Promise<LoginResult> {
  const raw = {
    username: formData.get("username"),
    password: formData.get("password")
  };

  console.log("Raw form data:", {
    username: raw.username,
    passwordProvided: !!raw.password
  });

  const data = {
    username: typeof raw.username === "string" ? raw.username.trim() : "",
    password: typeof raw.password === "string" ? raw.password : ""
  };

  console.log("Processed data:", {
    username: data.username,
    passwordLength: data.password.length
  });

  const parsed = loginSchema.safeParse(data);

  if (!parsed.success) {
    console.log("Validation failed:", parsed.error.flatten().fieldErrors);

    const errs: Record<string, string> = {};
    const fieldErrors = parsed.error.flatten().fieldErrors;

    for (const [field, msgs] of Object.entries(fieldErrors)) {
      if (msgs && msgs.length > 0) {
        errs[field] = msgs.join(", ");
      }
    }

    return { success: false, errors: errs };
  }



  try {
    console.log("Sending login request to:", "/v1/api/auth/login");
    console.log("Request payload:", {
      username: parsed.data.username,
      passwordLength: parsed.data.password.length
    });

    const resp = await api.post("/v1/api/auth/login", {
      username: parsed.data.username,
      password: parsed.data.password,
    });


    const cookieStore = await cookies()

    cookieStore.set("accessToken", resp.data.accessToken, {
      httpOnly: true,
      secure: true,
      sameSite: "lax",
      path: "/"
    });

    cookieStore.set("refreshToken", resp.data.refreshToken, {
      httpOnly: true,
      secure: true,
      sameSite: "lax",
      path: "/"
    });




    console.log("Login successful:", {
      status: resp.status,
      hasAccessToken: !!resp.data.accessToken,
      hasRefreshToken: !!resp.data.refreshToken
    });

    // Store tokens in localStorage or cookies
    if (typeof window !== 'undefined') {
      localStorage.setItem('accessToken', resp.data.accessToken);
      localStorage.setItem('refreshToken', resp.data.refreshToken);
    }

    return {
      success: true,
      accessToken: resp.data.accessToken,
      refreshToken: resp.data.refreshToken,
      username: parsed.data.username
    };

  } catch (err: unknown) {
    console.error("Login error:", err);

    const errs: Record<string, string> = {};

    if (err instanceof AxiosError) {
      console.log("Axios error details:", {
        status: err.response?.status,
        statusText: err.response?.statusText,
        data: err.response?.data
      });

      if (err.response?.status === 401 || err.response?.status === 403) {
        errs.general = "Invalid username or password";
      } else if (err.response?.data?.errors) {
        for (const [k, v] of Object.entries(err.response.data.errors as Record<string, unknown>)) {
          errs[k] = Array.isArray(v) ? v.join(", ") : String(v);
        }
      } else if (err.response?.data?.message) {
        errs.general = String(err.response.data.message);
      } else if (err.code === 'NETWORK_ERROR' || err.code === 'ECONNREFUSED') {
        errs.general = "Unable to connect to server. Please try again later.";
      } else {
        errs.general = "Login failed. Please try again.";
      }
    } else {
      errs.general = err instanceof Error ? err.message : "An unknown error occurred";
    }

    return { success: false, errors: errs };
  }
}


