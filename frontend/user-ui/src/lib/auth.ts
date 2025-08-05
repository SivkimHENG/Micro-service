import { cookies } from "next/headers";
import jwt from "jsonwebtoken";

export async function getUserSession(): Promise<{ role: string; sub: string } | null> {
  const cookie = await cookies()
  const token = cookie.get("accessToken")?.value;
  if (!token) return null;

  try {
    const payload = jwt.decode(token) as { sub: string; role: string; exp: number };
    if (!payload || payload.exp * 1000 < Date.now()) return null;
    return payload;
  } catch {
    return null;
  }
}
