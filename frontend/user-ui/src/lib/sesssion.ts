import { cookies } from "next/headers";

interface User {
  id: string
  username: string

}

export async function getAuthSession(): Promise<User | null> {
  try {
    const cookieStore = await cookies();
    const refreshToken = cookieStore.get("refreshToken")?.value;

    if (!refreshToken) return null;

    const response = await fetch("http://localhost:3000/v1/api/refresh", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${refreshToken}`,
      },
    });

    if (!response.ok) {
      console.error("Failed to refresh token");
      return null;
    }

    const data = await response.json();
    return data.user ?? null;
  } catch (error) {
    console.error("Error refreshing session:", error);
    return null;
  }
}
