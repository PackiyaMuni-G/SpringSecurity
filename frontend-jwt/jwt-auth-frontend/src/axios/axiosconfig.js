import axios from "axios";

// Set the base URL for Axios
axios.defaults.baseURL = "http://localhost:8080";

axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default axios;