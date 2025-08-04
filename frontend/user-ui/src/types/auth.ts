
export enum Role {
  CUSTOMER = "CUSTOMER",
  VENDOR = "VENDOR",
  ADMIN = "ADMIN",
  MODERATOR = "MODERATOR"
}

export interface User {

  id: string
  email: string
  username?: string
  role?: Role[]

}


export interface LoginResponse {
  refreshToken: string
  accessToken: string
  user: User
}



export interface RegisterResponse {
  message: string
  user: User
}


export interface ApiResponse<T> {
  success: boolean
  data?: T
  error?: string
  message?: string

}


