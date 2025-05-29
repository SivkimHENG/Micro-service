import axios from "axios";
import { AuthResponse, LoginRequest, RegisterRequest, User } from "../utils/user.utils";

const authApi = "http://localhost:8080/api/v1/auth/"


class AuthService {


    async authenticate(credential: string) {
        return axios.post(`${authApi}/authenticate` + credential);
    }


    logout(): void {
        localStorage.removeItem("user");
    }



    register(username: string, email: string, password: string): Promise<any> {
        const RegisterRequest: RegisterRequest = { username, email, password };
        return axios.post(authApi + "register", RegisterRequest);
    }

    getCurrentUser(): User | null {
        const userStr = localStorage.getItem("user");
        if (userStr) {
            return JSON.parse(userStr) as User;
        }
        return null;
    }

}

export default new AuthService();
