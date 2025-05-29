

export interface User {
    id: number
    username: string
    password: string
    email: string
    roles: string[]
    accessToken: string
    refreshToken?: string
}



export interface LoginRequest {
    username: string
    password: string

}
export interface RegisterRequest {
    username: string
    email: string
    password: string
    roles?: string[]
}


export interface AuthResponse {
    id: number;
    username: string;
    email: string;
    roles: string[];
    accessToken: string;
    refreshToken?: string;
    tokenType: string;
}
