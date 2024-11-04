// src/utils/axiosInstance.js
import axios from 'axios';
import {ACCESS_TOKEN} from "./Contants/Constants.jsx";

const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Your backend base URL
});

// Set up request interceptor to include JWT token in headers
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem(ACCESS_TOKEN);
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;


// import axios from 'axios';
//
// // Configure Axios instance
// const api = axios.create({
//     baseURL: 'http://localhost:8080/api', // Your backend base URL
// });
//
// // Set up request interceptor to include JWT token in headers
// api.interceptors.request.use(
//     (config) => {
//         const token = localStorage.getItem('token');
//         if (token) {
//             config.headers['Authorization'] = `Bearer ${token}`;
//         }
//         return config;
//     },
//     (error) => Promise.reject(error)
// );
//
// export default api;
