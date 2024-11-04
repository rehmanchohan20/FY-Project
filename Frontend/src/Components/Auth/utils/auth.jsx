// src/utils/auth.js
import Cookies from 'js-cookie';

export const setToken = (token) => {
    Cookies.set('accessToken', token, { expires: 7 }); // Set token in a cookie for 7 days
};

export const getToken = () => {
    return Cookies.get('accessToken'); // Get token from cookie
};

export const removeToken = () => {
    Cookies.remove('accessToken'); // Remove token from cookie
};
