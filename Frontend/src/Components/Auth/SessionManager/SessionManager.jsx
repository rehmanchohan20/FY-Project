// src/components/SessionManager.js
import React, { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { getToken, removeToken } from '../utils/auth.jsx';

const SessionManager = ({ children }) => {
    useEffect(() => {
        const token = getToken();
        if (!token) {
            removeToken(); // Ensure token is removed if not found
            window.location.href = '/login'; // Redirect to login if no token
        }
    }, []);

    return <>{children}</>; // Render children if token exists
};

export default SessionManager;
