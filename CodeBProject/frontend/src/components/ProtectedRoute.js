import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const ProtectedRoute = ({ children, allowedRoles = [] }) => {
    const location = useLocation();
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

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