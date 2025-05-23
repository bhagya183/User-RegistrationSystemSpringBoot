import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../../services/api';
import './Auth.css';

const SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes in milliseconds

const Login = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        // Check if user is already logged in
        const token = localStorage.getItem('token');
        const lastActivity = localStorage.getItem('lastActivity');
        
        if (token && lastActivity) {
            const now = new Date().getTime();
            const timeSinceLastActivity = now - parseInt(lastActivity);
            
            if (timeSinceLastActivity < SESSION_TIMEOUT) {
                // Session is still valid, redirect to dashboard
                navigate('/dashboard');
            } else {
                // Session expired, clear storage
                handleLogout();
            }
        }

        // Set up activity listeners
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
    }, [navigate]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('lastActivity');
        navigate('/login');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            console.log('Attempting login with:', { email: formData.email });
            const response = await authAPI.login(formData);
            console.log('Login response:', response.data);
            
            const { token, role } = response.data;
            
            if (!token) {
                throw new Error('No token received from server');
            }
            
            // Store auth data
            localStorage.setItem('token', token);
            localStorage.setItem('role', role);
            localStorage.setItem('lastActivity', new Date().getTime().toString());
            
            console.log('Stored role:', role); // Debug log
            
            // Redirect based on role
            if (role === 'ADMIN') {
                navigate('/dashboard');
            } else {
                navigate('/dashboard');
            }
        } catch (err) {
            console.error('Login error:', err);
            const errorMessage = err.response?.data || err.message || 'Login failed. Please try again.';
            setError(typeof errorMessage === 'string' ? errorMessage : 'Login failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h2>Welcome Back</h2>
                <p className="subtitle">Please login to your account</p>
                
                {error && <div className="error-message">{error}</div>}
                
                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="Enter your email"
                            disabled={loading}
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password"
                            disabled={loading}
                        />
                    </div>
                    
                    <button 
                        type="submit" 
                        className="auth-button"
                        disabled={loading}
                    >
                        {loading ? 'Logging in...' : 'Login'}
                    </button>
                </form>
                
                <div className="auth-links">
                    <Link to="/forgot-password" className="forgot-password">Forgot Password?</Link>
                    <p className="register-link">
                        Don't have an account? <Link to="/register">Register</Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login; 