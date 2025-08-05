import { NextResponse, NextRequest } from "next/server";
import jwt from "jsonwebtoken";


export function middleware(req: NextRequest) {
  const token = req.cookies.get("accesstoken")?.value;

  if (!token) {
    return NextResponse.redirect(new URL("/v1/api/auth/login", req.url));
  }


  try {

    const decoded = jwt.decode(token) as { role?: string, exp?: number }
    if (!decoded || (decoded.exp && decoded.exp * 1000 < Date.now())) {
      return NextResponse.redirect(new URL("/auth/login", req.url));
    }

    if (req.nextUrl.pathname.startsWith("/admin") && decoded.role !== "ADMIN") {
      return NextResponse.redirect(new URL("/unauthorized", req.url));
    }

    return NextResponse.next();
  } catch {
    return NextResponse.redirect(new URL("/auth/login", req.url));
  }
}
export const config = {
  matcher: [
    "/admin/:path*",
    "/user/home",
  ],

}
