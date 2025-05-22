import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        const role = localStorage.getItem('role');
        
        if (!token) {
            navigate('/login');
            return;
        }

        setUser({ role });
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        navigate('/login');
    };

    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <div className="dashboard">
            <nav className="dashboard-nav">
                <h1>Dashboard</h1>
                <button onClick={handleLogout} className="logout-button">
                    Logout
                </button>
            </nav>
            
            <div className="dashboard-content">
                <h2>Welcome to your Dashboard</h2>
                <p>Your role: {user.role}</p>
                
                <div className="dashboard-cards">
                    <div className="card">
                        <h3>Profile</h3>
                        <p>View and edit your profile information</p>
                    </div>
                    
                    <div className="card">
                        <h3>Settings</h3>
                        <p>Manage your account settings</p>
                    </div>
                    
                    <div className="card">
                        <h3>Activity</h3>
                        <p>View your recent activity</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard; 