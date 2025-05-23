import React from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
    const navigate = useNavigate();

    return (
        <div className="dashboard-layout">
            <nav className="dashboard-nav">
                <div className="nav-header">
                    <h2>Dashboard</h2>
                </div>
                <ul>
                    <li>
                        <Link to="/dashboard">Home</Link>
                    </li>
                    <li>
                        <Link to="/dashboard/group">Group Management</Link>
                    </li>
                    <li>
                        <Link to="/dashboard/chain">Chain Management</Link>
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