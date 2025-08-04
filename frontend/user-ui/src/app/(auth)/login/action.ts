"use server"
import z, { email } from "zod";

const passwordSchema = z.string()
  .min(12, { message: "Password must be at least 12 characters long" })
  .max(64, { message: "Password must not exceed 64 characters" })
  .regex(/[A-Z]/, { message: "Password must include at least one uppercase letter" })
  .regex(/[a-z]/, { message: "Password must include at least one lowercase letter" })
  .regex(/\d/, { message: "Password must include at least one number" })
  .regex(/[^A-Za-z0-9]/, { message: "Password must include at least one special character" })
  .regex(/^\S*$/, { message: "Password must not contain spaces" });

const loginSchema = z.object({

  email: z.email({ message: "Invalid Email" }),
  password: passwordSchema

});

export type LoginResult = | { success: true, userId: string }
  | { success: false, error: Record<string, string> }


export async function LoginAction(formData: FormData): Promise<LoginResult> {
  const rawEmail = formData.get("email")
  const rawPassword = formData.get("password")

  const data = {
    email: typeof rawEmail === "string" ? rawEmail : "",
    password: typeof rawPassword === "string" ? rawPassword : ""
  }

  const result = loginSchema.safeParse(data);

  if (!result.success) {


    const errors: Record<string, string> = {};
    result.error.flatten((field, msgs) => {
      if (msgs?.length) errors[field] = msgs.join(", ");
    });

    return { success: false, errors };
  }

}

