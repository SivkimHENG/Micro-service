
"use client";

import { LogoutAction } from "@/app/(auth)/logout/action";
import { useRouter } from "next/navigation";
import { startTransition } from "react";

export default function LogoutButton() {
  const router = useRouter();

  async function handleLogout() {
    "use server";
    await LogoutAction();
    startTransition(() => {
      router.push("/auth/login");
    });
  }

  return <button onClick={handleLogout}>Logout</button>;
}
