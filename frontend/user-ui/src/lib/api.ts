import axios, { AxiosError, AxiosResponse } from 'axios';



const api = axios.create({
  headers: {
    "Content-Type": "application/json",

  },
  baseURL: "http://localhost:8082",
  withCredentials: true
});







export default api;



