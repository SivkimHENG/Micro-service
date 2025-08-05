"use server";

import api from "@/lib/api";
import { AxiosError } from "axios";
import { z } from "zod";

const passwordSchema = z
  .string()
  .min(8, { message: "Must be at least 12 characters" })
  .max(64, { message: "Must not exceed 64 characters" })
  .regex(/[A-Z]/, { message: "Must include an uppercase letter" })
  .regex(/[a-z]/, { message: "Must include a lowercase letter" })
  .regex(/\d/, { message: "Must include a number" })
  .regex(/[^A-Za-z0-9]/, { message: "Must include a special character" })
  .regex(/^\S*$/, { message: "No spaces allowed" });

const registerSchema = z
  .object({
    username: z.string().min(3, "At least 3 characters"),
    email: z.string().email({ message: "Invalid email address" }),
    password: passwordSchema,
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Passwords do not match",
  });

export type RegisterResult =
  | { success: true; userId: string }
  | { success: false; errors: Record<string, string> };

export async function RegisterAction(
  formData: FormData
): Promise<RegisterResult> {
  const raw = {
    username: formData.get("username"),
    email: formData.get("email"),
    password: formData.get("password"),
    confirmPassword: formData.get("confirmPassword"),
  };
  const data = {
    username: typeof raw.username === "string" ? raw.username : "",
    email: typeof raw.email === "string" ? raw.email : "",
    password: typeof raw.password === "string" ? raw.password : "",
    confirmPassword: typeof raw.confirmPassword === "string" ? raw.confirmPassword : "",
  };

  const parsed = registerSchema.safeParse(data);
  if (!parsed.success) {
    const errs: Record<string, string> = {};
    const fieldErrors = parsed.error.flatten().fieldErrors;
    for (const [field, msgs] of Object.entries(fieldErrors)) {
      if (msgs && msgs.length > 0) errs[field] = msgs.join(", ");
    }
    return { success: false, errors: errs };
  }

  try {
    const resp = await api.post("/v1/api/auth/register", {
      username: parsed.data.username,
      email: parsed.data.email,
      password: parsed.data.password,
    });

    return { success: true, userId: resp.data.id as string };
  } catch (err: unknown) {
    const errs: Record<string, string> = {};

    if (err instanceof AxiosError) {
      if (err.response?.data?.errors) {
        for (const [k, v] of Object.entries(err.response.data.errors as any)) {
          errs[k] = Array.isArray(v) ? v.join(", ") : String(v);
        }
      } else if (err.response?.data?.message) {
        errs.general = String(err.response.data.message);
      } else {
        errs.general = err.message;
      }
    } else {
      errs.general = (err as Error).message;
    }

    return { success: false, errors: errs };
  }
}
