"use server"

import { cookies } from "next/headers"

export async function LogoutAction() {

  const cookieStore = await cookies();
  cookieStore.delete("accessToken");
  cookieStore.delete("refreshToken");

}
