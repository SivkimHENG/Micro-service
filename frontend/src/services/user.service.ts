import axios from "axios";
import { authHeader } from "./auth-header";


const authApi = "http://localhost:8080/api/v1/auth/";


interface ContentResponse {
    content: string
}

export class UserService {

    getPublicContent(): Promise<ContentResponse> {
        return axios.get<ContentResponse>(authApi + "user", { headers: authHeader() });
    }

}

export default new UserService();
