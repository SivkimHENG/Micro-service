import api from "./api";




export async function login(username: string, password: string) {
  const response = await api.post("/v1/api/auth/login", { username, password });
  return response.data;
}

export async function register(payload: { username: string; email: string; password: string }) {

  const response = await api.post("/v1/api/auth/register", payload);
  return response.data;
}

export async function refreshToken(refreshToken: string) {
  const response = await api.post("v1/api/auth/refresh", { refreshToken });
  return response.data;
}


export async function getProfile() {
  const response = await api.get("/v1/api/auth/profile");
  return response.data;
}

export async function changePassword(payload: { oldPassword: string; newPassword: string; confirmPassword: string }) {
  const response = await api.post("/v1/api/auth/change-password", payload);
  return response.data;
}

export async function logout() {
  await api.post("/v1/api/auth/logout");
}

export async function validateToken(token: string) {
  const response = await api.post(
    "v1/api/auth/validate", {},
    {

      headers: {
        Authorization: `Bearer ${token}`,

      },
    }
  )
  return response.data;

}


