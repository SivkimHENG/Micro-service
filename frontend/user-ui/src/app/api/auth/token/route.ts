import { NextApiRequest } from "next";
import { ReadonlyRequestCookies } from "next/dist/server/web/spec-extension/adapters/request-cookies";
import { cookies } from "next/headers";




export async function GET(req: NextApiRequest): Promise<ReadonlyRequestCookies> {

  const authToken = cookies().





}

