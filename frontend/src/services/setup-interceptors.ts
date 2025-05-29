import axios, { AxiosError, AxiosRequestConfig } from "axios";
import AuthService from "../services/auth.service";


class AxiosInterceptor {
    private axiosInstance;
    private isRefresh: boolean;
    private refreshSubscribers: string[] = [];


    constructor(instanceConfig = {}) {
        this.isRefresh = false;
        this.refreshSubscribers = [];
        this.axiosInstance = axios.create({
            ...instanceConfig
        });
        this.axiosInstance.interceptors.request.use(
            (config) => {
                const accessToken = this.getAccessToken;
                if (accessToken) {
                    config.headers.Authorization = `Bearer ${accessToken}`;
                }
                return config;
            },

            (error) => Promise.reject(error),
        );

        this.axiosInstance.interceptors.response.use(
            (response) => response,
            async (error) => {
                const originalRequest = error.config;


                if (
                    error.response &&
                    error.response.status === 401 &&
                    error.response.data === "TokenExpired" &&
                    !originalRequest.retry) {
                    if (!this.isRefresh) {
                        this.isRefresh = true;
                        try {
                            const newTokens = await this.refreshTokens();
                            this.setAccessToken(newTokens.accessToken);
                            this.setRefreshToken(newTokens.refreshToken);

                            this.refreshSubscribers.forEach((cb) => cb(newTokens, accessToken)

                            )


                        } catch (error: any) {
                            console.log("message -> ", error);
                        }
                    }
                }
            })

        this.get = this.axiosInstance.get.bind(this.axiosInstance);
        this.post = this.axiosInstance.post.bind(this.axiosInstance);
        this.put = this.axiosInstance.put.bind(this.axiosInstance);
        this.delete = this.axiosInstance.delete.bind(this.axiosInstance);
    }

    getAccessToken() {
        return localStorage.getItem("accessToken");
    }

    getFreshToken() {
        return localStorage.getItem("refreshToken");
    }

    setAccessToken(token: string) {
        return localStorage.setItem("accessToken", token)
    }

    setRefreshToken(token: string) {
        return localStorage.setItem("refreshToken", token)
    }


    async refreshTokens() {
        const refreshToken = this.getFreshToken();
        if (!refreshToken) {
            throw new Error("No fresh token available");
        }

        const response = await this.axiosInstance.post("auth/refreshToken", {
            refreshToken,
        });
        return response.data;
    }
}

export const client = new AxiosInterceptor({
    baseURL: "http://localhost:8080/api/v1"

});
