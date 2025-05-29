import axios from "axios";

import Cookies from "js-cookie";

const API_URL = "http://localhost:8080/api/v1";




const api = axios.create({

    baseURL: API_URL,
    withCredentials: true,
    headers: {
        "Content-Type": "application/json",
    }
});

api.interceptors.request.use(config => {
    const token = Cookies.get("XSRF-TOKEN");
    if (token) config.headers!["X-XSRF-TOKEN"] = token;
    return config;
});


api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response.status === 401) {
            localStorage.removeItem("token");
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
)




export default api;
