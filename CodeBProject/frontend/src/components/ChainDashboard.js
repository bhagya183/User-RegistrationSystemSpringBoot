import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ChainDashboard.css';

const ChainDashboard = () => {
    const [chains, setChains] = useState([]);
    const [groups, setGroups] = useState([]);
    const [showAddForm, setShowAddForm] = useState(false);
    const [showEditForm, setShowEditForm] = useState(false);
    const [selectedChain, setSelectedChain] = useState(null);
    const [formData, setFormData] = useState({
        companyName: '',
        gstnNo: '',
        groupId: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [selectedGroup, setSelectedGroup] = useState('');

    const API_URL = 'http://localhost:8080/api';
    const CHAINS_URL = `${API_URL}/chains`;
    const GROUPS_URL = `${API_URL}/groups`;

    useEffect(() => {
        fetchGroups();
        fetchChains();
    }, []);

    useEffect(() => {
        if (selectedGroup) {
            fetchChainsByGroup(selectedGroup);
        } else {
            fetchChains();
        }
    }, [selectedGroup]);

    const fetchGroups = async () => {
        try {
            const response = await axios.get(GROUPS_URL);
            setGroups(response.data);
        } catch (error) {
            setError('Failed to fetch groups');
            console.error('Error fetching groups:', error);
        }
    };

    const fetchChains = async () => {
        try {
            setLoading(true);
            const response = await axios.get(CHAINS_URL);
            setChains(response.data);
            setError('');
        } catch (error) {
            setError('Failed to fetch chains');
            console.error('Error fetching chains:', error);
        } finally {
            setLoading(false);
        }
    };

    const fetchChainsByGroup = async (groupId) => {
        try {
            setLoading(true);
            const response = await axios.get(`${CHAINS_URL}?groupId=${groupId}`);
            setChains(response.data);
            setError('');
        } catch (error) {
            setError('Failed to fetch chains for the selected group');
            console.error('Error fetching chains by group:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
        setError('');
    };

    const handleAddChain = async (e) => {
        e.preventDefault();
        try {
            await axios.post(CHAINS_URL, formData);
            setShowAddForm(false);
            setFormData({ companyName: '', gstnNo: '', groupId: '' });
            fetchChains();
            setError('');
        } catch (error) {
            setError(error.response?.data?.message || 'Failed to add chain');
            console.error('Error adding chain:', error);
        }
    };

    const handleEditChain = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`${CHAINS_URL}/${selectedChain.chainId}`, formData);
            setShowEditForm(false);
            setSelectedChain(null);
            setFormData({ companyName: '', gstnNo: '', groupId: '' });
            fetchChains();
            setError('');
        } catch (error) {
            setError(error.response?.data?.message || 'Failed to update chain');
            console.error('Error updating chain:', error);
        }
    };

    const handleDeleteChain = async (chainId) => {
        if (window.confirm('Are you sure you want to delete this chain?')) {
            try {
                await axios.delete(`${CHAINS_URL}/${chainId}`);
                fetchChains();
                setError('');
            } catch (error) {
                setError('Failed to delete chain');
                console.error('Error deleting chain:', error);
            }
        }
    };

    const openEditForm = (chain) => {
        setSelectedChain(chain);
        setFormData({
            companyName: chain.companyName,
            gstnNo: chain.gstnNo,
            groupId: chain.group.groupId
        });
        setShowEditForm(true);
        setError('');
    };

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="dashboard-container">
            <h1>Chain Management Dashboard</h1>
            
            {error && <div className="error-message">{error}</div>}
            
            <div className="filter-section">
                <select 
                    value={selectedGroup} 
                    onChange={(e) => setSelectedGroup(e.target.value)}
                    className="group-filter"
                >
                    <option value="">All Groups</option>
                    {groups.map(group => (
                        <option key={group.groupId} value={group.groupId}>
                            {group.groupName}
                        </option>
                    ))}
                </select>
            </div>

            <button 
                className="add-button"
                onClick={() => setShowAddForm(true)}
            >
                Add New Chain
            </button>

            {showAddForm && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Add New Chain</h2>
                        <form onSubmit={handleAddChain}>
                            <div className="form-group">
                                <label>Company Name</label>
                                <input
                                    type="text"
                                    name="companyName"
                                    value={formData.companyName}
                                    onChange={handleInputChange}
                                    placeholder="Enter company name"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>GSTN Number</label>
                                <input
                                    type="text"
                                    name="gstnNo"
                                    value={formData.gstnNo}
                                    onChange={handleInputChange}
                                    placeholder="Enter GSTN number"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Group</label>
                                <select
                                    name="groupId"
                                    value={formData.groupId}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Select a group</option>
                                    {groups.map(group => (
                                        <option key={group.groupId} value={group.groupId}>
                                            {group.groupName}
                                        </option>
                                    ))}
                                </select>
                            </div>
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
                        <h2>Edit Chain</h2>
                        <form onSubmit={handleEditChain}>
                            <div className="form-group">
                                <label>Company Name</label>
                                <input
                                    type="text"
                                    name="companyName"
                                    value={formData.companyName}
                                    onChange={handleInputChange}
                                    placeholder="Enter company name"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>GSTN Number</label>
                                <input
                                    type="text"
                                    name="gstnNo"
                                    value={formData.gstnNo}
                                    onChange={handleInputChange}
                                    placeholder="Enter GSTN number"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Group</label>
                                <select
                                    name="groupId"
                                    value={formData.groupId}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Select a group</option>
                                    {groups.map(group => (
                                        <option key={group.groupId} value={group.groupId}>
                                            {group.groupName}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="button-group">
                                <button type="submit">Update</button>
                                <button type="button" onClick={() => setShowEditForm(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            <div className="chains-list">
                <table>
                    <thead>
                        <tr>
                            <th>Company Name</th>
                            <th>GSTN Number</th>
                            <th>Group</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {chains.length === 0 ? (
                            <tr>
                                <td colSpan="5" style={{ textAlign: 'center' }}>No chains found</td>
                            </tr>
                        ) : (
                            chains.map((chain) => (
                                <tr key={chain.chainId}>
                                    <td>{chain.companyName}</td>
                                    <td>{chain.gstnNo}</td>
                                    <td>{chain.group.groupName}</td>
                                    <td>{new Date(chain.createdAt).toLocaleDateString()}</td>
                                    <td>
                                        <button 
                                            className="edit-button"
                                            onClick={() => openEditForm(chain)}
                                        >
                                            Edit
                                        </button>
                                        <button 
                                            className="delete-button"
                                            onClick={() => handleDeleteChain(chain.chainId)}
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

export default ChainDashboard; 