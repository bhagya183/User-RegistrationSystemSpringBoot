import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import ForgotPassword from './components/auth/ForgotPassword';
import ResetPassword from './components/auth/ResetPassword';
import Dashboard from './components/Dashboard';
import GroupDashboard from './components/GroupDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import ChainDashboard from './components/ChainDashboard';
import BrandDashboard from './components/BrandDashboard';
import './App.css';

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        
        {/* Protected routes */}
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>}>
          <Route index element={<div>Welcome to Dashboard</div>} />
          <Route path="group" element={<GroupDashboard />} />
          <Route path="chain" element={<ChainDashboard />} />
          <Route path="brand" element={<BrandDashboard />} />
        </Route>
        
        {/* Default route - redirect to login */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
};

export default App; 