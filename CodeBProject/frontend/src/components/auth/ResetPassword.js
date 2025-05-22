import React, { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import './Auth.css';

const ResetPassword = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    const navigate = useNavigate();
    
    const [formData, setFormData] = useState({
        password: '',
        confirmPassword: ''
    });
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (formData.password !== formData.confirmPassword) {
            setError('Passwords do not match');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/auth/reset-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token,
                    password: formData.password
                })
            });

            const data = await response.text();
            
            if (response.ok) {
                setMessage('Password has been reset successfully');
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            } else {
                setError(data || 'Failed to reset password');
            }
        } catch (err) {
            setError('An error occurred. Please try again.');
        }
    };

    if (!token) {
        return (
            <div className="auth-container">
                <div className="auth-box">
                    <div className="error-message">Invalid or missing reset token</div>
                    <div className="auth-links">
                        <a href="/forgot-password">Request a new reset link</a>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h2>Reset Password</h2>
                <p className="subtitle">Enter your new password</p>
                
                {error && <div className="error-message">{error}</div>}
                {message && <div className="success-message">{message}</div>}
                
                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="form-group">
                        <label htmlFor="password">New Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter new password"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm New Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                            placeholder="Confirm new password"
                        />
                    </div>
                    
                    <button type="submit" className="auth-button">Reset Password</button>
                </form>
            </div>
        </div>
    );
};

export default ResetPassword; 