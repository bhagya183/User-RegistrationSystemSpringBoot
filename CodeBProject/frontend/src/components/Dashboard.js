import React from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
    const navigate = useNavigate();
    const role = localStorage.getItem('role');
    const isAdmin = role === 'ADMIN';

    // Debug logging
    console.log('Current role:', role);
    console.log('Is admin:', isAdmin);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('lastActivity');
        navigate('/login');
    };

    return (
        <div className="dashboard-layout">
            <nav className="dashboard-nav">
                <div className="nav-header">
                    <h2>Dashboard</h2>
                    <p className="user-role">{role}</p>
                </div>
                <ul>
                    <li>
                        <Link to="/dashboard">Home</Link>
                    </li>
                    {isAdmin && (
                        <>
                            <li>
                                <Link to="/dashboard/group">Group Management</Link>
                            </li>
                            <li>
                                <Link to="/dashboard/chain">Chain Management</Link>
                            </li>
                            <li>
                                <Link to="/dashboard/brand">Brand Management</Link>
                            </li>
                        </>
                    )}
                    <li>
                        <button onClick={handleLogout} className="logout-button">
                            Logout
                        </button>
                    </li>
                </ul>
            </nav>
            
            <main className="dashboard-content">
                <Outlet />
            </main>
        </div>
    );
};

export default Dashboard; 