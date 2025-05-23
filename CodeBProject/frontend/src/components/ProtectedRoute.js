import React, { useEffect } from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes in milliseconds

const ProtectedRoute = ({ children, allowedRoles = [] }) => {
    const location = useLocation();
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    const lastActivity = localStorage.getItem('lastActivity');

    useEffect(() => {
        // Check session timeout
        if (lastActivity) {
            const now = new Date().getTime();
            const timeSinceLastActivity = now - parseInt(lastActivity);
            
            if (timeSinceLastActivity >= SESSION_TIMEOUT) {
                // Session expired, clear storage and redirect to login
                localStorage.removeItem('token');
                localStorage.removeItem('role');
                localStorage.removeItem('lastActivity');
                window.location.href = '/login';
            }
        }

        // Update last activity on user interaction
        const updateLastActivity = () => {
            localStorage.setItem('lastActivity', new Date().getTime().toString());
        };

        window.addEventListener('mousemove', updateLastActivity);
        window.addEventListener('keypress', updateLastActivity);
        window.addEventListener('click', updateLastActivity);

        return () => {
            window.removeEventListener('mousemove', updateLastActivity);
            window.removeEventListener('keypress', updateLastActivity);
            window.removeEventListener('click', updateLastActivity);
        };
    }, []);

    if (!token) {
        // Redirect to login if not authenticated
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(role)) {
        // Redirect to dashboard if user doesn't have required role
        return <Navigate to="/dashboard" replace />;
    }

    return children;
};

export default ProtectedRoute; 