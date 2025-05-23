import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './GroupDashboard.css';

const GroupDashboard = () => {
    const [groups, setGroups] = useState([]);
    const [showAddForm, setShowAddForm] = useState(false);
    const [showEditForm, setShowEditForm] = useState(false);
    const [selectedGroup, setSelectedGroup] = useState(null);
    const [formData, setFormData] = useState({ groupName: '' });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    const API_URL = 'http://localhost:8080/api/groups';

    useEffect(() => {
        fetchGroups();
    }, []);

    const fetchGroups = async () => {
        try {
            setLoading(true);
            const response = await axios.get(API_URL);
            setGroups(response.data);
            setError('');
        } catch (error) {
            setError('Failed to fetch groups. Please try again later.');
            console.error('Error fetching groups:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
        setError('');
    };

    const handleAddGroup = async (e) => {
        e.preventDefault();
        try {
            await axios.post(API_URL, formData);
            setShowAddForm(false);
            setFormData({ groupName: '' });
            fetchGroups();
            setError('');
        } catch (error) {
            setError(error.response?.data?.message || 'Failed to add group. Please try again.');
            console.error('Error adding group:', error);
        }
    };

    const handleEditGroup = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`${API_URL}/${selectedGroup.groupId}`, formData);
            setShowEditForm(false);
            setSelectedGroup(null);
            setFormData({ groupName: '' });
            fetchGroups();
            setError('');
        } catch (error) {
            setError(error.response?.data?.message || 'Failed to update group. Please try again.');
            console.error('Error updating group:', error);
        }
    };

    const handleDeleteGroup = async (groupId) => {
        if (window.confirm('Are you sure you want to delete this group?')) {
            try {
                await axios.delete(`${API_URL}/${groupId}`);
                fetchGroups();
                setError('');
            } catch (error) {
                setError('Failed to delete group. Please try again.');
                console.error('Error deleting group:', error);
            }
        }
    };

    const openEditForm = (group) => {
        setSelectedGroup(group);
        setFormData({ groupName: group.groupName });
        setShowEditForm(true);
        setError('');
    };

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="dashboard-container">
            <h1>Group Management Dashboard</h1>
            
            {error && <div className="error-message">{error}</div>}
            
            <button 
                className="add-button"
                onClick={() => setShowAddForm(true)}
            >
                Add New Group
            </button>

            {showAddForm && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Add New Group</h2>
                        <form onSubmit={handleAddGroup}>
                            <input
                                type="text"
                                name="groupName"
                                value={formData.groupName}
                                onChange={handleInputChange}
                                placeholder="Enter group name"
                                required
                            />
                            <div className="button-group">
                                <button type="submit">Add</button>
                                <button type="button" onClick={() => setShowAddForm(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {showEditForm && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Edit Group</h2>
                        <form onSubmit={handleEditGroup}>
                            <input
                                type="text"
                                name="groupName"
                                value={formData.groupName}
                                onChange={handleInputChange}
                                placeholder="Enter group name"
                                required
                            />
                            <div className="button-group">
                                <button type="submit">Update</button>
                                <button type="button" onClick={() => setShowEditForm(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            <div className="groups-list">
                <table>
                    <thead>
                        <tr>
                            <th>Group Name</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {groups.length === 0 ? (
                            <tr>
                                <td colSpan="3" style={{ textAlign: 'center' }}>No groups found</td>
                            </tr>
                        ) : (
                            groups.map((group) => (
                                <tr key={group.groupId}>
                                    <td>{group.groupName}</td>
                                    <td>{new Date(group.createdAt).toLocaleDateString()}</td>
                                    <td>
                                        <button 
                                            className="edit-button"
                                            onClick={() => openEditForm(group)}
                                        >
                                            Edit
                                        </button>
                                        <button 
                                            className="delete-button"
                                            onClick={() => handleDeleteGroup(group.groupId)}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default GroupDashboard; 