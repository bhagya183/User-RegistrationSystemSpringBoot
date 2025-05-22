import React, { useState } from 'react';
import './Auth.css';

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/auth/forgot-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email })
            });

            const data = await response.text();
            
            if (response.ok) {
                setMessage('Password reset link has been sent to your email');
                setError('');
            } else {
                setError(data || 'Failed to send reset link');
                setMessage('');
            }
        } catch (err) {
            setError('An error occurred. Please try again.');
            setMessage('');
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h2>Forgot Password</h2>
                <p className="subtitle">Enter your email to reset your password</p>
                
                {error && <div className="error-message">{error}</div>}
                {message && <div className="success-message">{message}</div>}
                
                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            placeholder="Enter your email"
                        />
                    </div>
                    
                    <button type="submit" className="auth-button">Send Reset Link</button>
                </form>
                
                <div className="auth-links">
                    <p className="login-link">
                        Remember your password? <a href="/login">Login</a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default ForgotPassword; 