import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './BrandDashboard.css';

const BrandDashboard = () => {
    const [brands, setBrands] = useState([]);
    const [chains, setChains] = useState([]);
    const [groups, setGroups] = useState([]);
    const [showAddForm, setShowAddForm] = useState(false);
    const [showEditForm, setShowEditForm] = useState(false);
    const [selectedBrand, setSelectedBrand] = useState(null);
    const [formData, setFormData] = useState({
        brandName: '',
        chainId: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [selectedChain, setSelectedChain] = useState('');
    const [selectedGroup, setSelectedGroup] = useState('');

    const API_URL = 'http://localhost:8080/api';
    const BRANDS_URL = `${API_URL}/brands`;
    const CHAINS_URL = `${API_URL}/chains`;
    const GROUPS_URL = `${API_URL}/groups`;

    useEffect(() => {
        console.log('Initial data fetch started');
        const fetchInitialData = async () => {
            try {
                await Promise.all([
                    fetchGroups(),
                    fetchChains(),
                    fetchBrands()
                ]);
            } catch (error) {
                console.error('Error fetching initial data:', error);
                setError('Failed to load initial data');
            }
        };
        fetchInitialData();
    }, []);

    useEffect(() => {
        console.log('Filter changed - Chain:', selectedChain, 'Group:', selectedGroup);
        if (selectedChain) {
            fetchBrandsByChain(selectedChain);
        } else if (selectedGroup) {
            fetchBrandsByGroup(selectedGroup);
        } else {
            fetchBrands();
        }
    }, [selectedChain, selectedGroup]);

    const fetchGroups = async () => {
        try {
            console.log('Fetching groups from:', GROUPS_URL);
            const response = await axios.get(GROUPS_URL);
            console.log('Groups fetched:', response.data);
            setGroups(response.data);
        } catch (error) {
            console.error('Error fetching groups:', error);
            setError('Failed to fetch groups: ' + (error.response?.data || error.message));
        }
    };

    const fetchChains = async () => {
        try {
            console.log('Fetching chains from:', CHAINS_URL);
            const response = await axios.get(CHAINS_URL);
            console.log('Chains fetched:', response.data);
            setChains(response.data);
        } catch (error) {
            console.error('Error fetching chains:', error);
            setError('Failed to fetch chains: ' + (error.response?.data || error.message));
        }
    };

    const fetchBrands = async () => {
        try {
            setLoading(true);
            console.log('Fetching brands from:', BRANDS_URL);
            const response = await axios.get(BRANDS_URL);
            console.log('Brands fetched:', response.data);
            setBrands(response.data);
            setError('');
        } catch (error) {
            console.error('Error fetching brands:', error);
            setError('Failed to fetch brands: ' + (error.response?.data || error.message));
        } finally {
            setLoading(false);
        }
    };

    const fetchBrandsByChain = async (chainId) => {
        try {
            setLoading(true);
            const url = `${BRANDS_URL}?chainId=${chainId}`;
            console.log('Fetching brands by chain from:', url);
            const response = await axios.get(url);
            console.log('Brands by chain fetched:', response.data);
            setBrands(response.data);
            setError('');
        } catch (error) {
            console.error('Error fetching brands by chain:', error);
            setError('Failed to fetch brands for the selected chain: ' + (error.response?.data || error.message));
        } finally {
            setLoading(false);
        }
    };

    const fetchBrandsByGroup = async (groupId) => {
        try {
            setLoading(true);
            const url = `${BRANDS_URL}?groupId=${groupId}`;
            console.log('Fetching brands by group from:', url);
            const response = await axios.get(url);
            console.log('Brands by group fetched:', response.data);
            setBrands(response.data);
            setError('');
        } catch (error) {
            console.error('Error fetching brands by group:', error);
            setError('Failed to fetch brands for the selected group: ' + (error.response?.data || error.message));
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
        setError('');
    };

    const handleAddBrand = async (e) => {
        e.preventDefault();
        try {
            console.log('Adding brand:', formData);
            const response = await axios.post(BRANDS_URL, formData);
            console.log('Brand added successfully:', response.data);
            setShowAddForm(false);
            setFormData({ brandName: '', chainId: '' });
            fetchBrands();
            setError('');
        } catch (error) {
            console.error('Error adding brand:', error);
            setError(error.response?.data || 'Failed to add brand');
        }
    };

    const handleEditBrand = async (e) => {
        e.preventDefault();
        try {
            console.log('Updating brand:', selectedBrand.brandId, formData);
            const response = await axios.put(`${BRANDS_URL}/${selectedBrand.brandId}`, formData);
            console.log('Brand updated successfully:', response.data);
            setShowEditForm(false);
            setSelectedBrand(null);
            setFormData({ brandName: '', chainId: '' });
            fetchBrands();
            setError('');
        } catch (error) {
            console.error('Error updating brand:', error);
            setError(error.response?.data || 'Failed to update brand');
        }
    };

    const handleDeleteBrand = async (brandId) => {
        if (window.confirm('Are you sure you want to delete this brand?')) {
            try {
                console.log('Deleting brand:', brandId);
                await axios.delete(`${BRANDS_URL}/${brandId}`);
                console.log('Brand deleted successfully');
                fetchBrands();
                setError('');
            } catch (error) {
                console.error('Error deleting brand:', error);
                setError('Failed to delete brand: ' + (error.response?.data || error.message));
            }
        }
    };

    const openEditForm = (brand) => {
        console.log('Opening edit form for brand:', brand);
        setSelectedBrand(brand);
        setFormData({
            brandName: brand.brandName,
            chainId: brand.chain.chainId
        });
        setShowEditForm(true);
        setError('');
    };

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="dashboard-container">
            <h1>Brand Management Dashboard</h1>
            
            {error && <div className="error-message">{error}</div>}
            
            <div className="filter-section">
                <select 
                    value={selectedGroup} 
                    onChange={(e) => {
                        setSelectedGroup(e.target.value);
                        setSelectedChain('');
                    }}
                    className="group-filter"
                >
                    <option value="">All Groups</option>
                    {groups.map(group => (
                        <option key={group.groupId} value={group.groupId}>
                            {group.groupName}
                        </option>
                    ))}
                </select>

                <select 
                    value={selectedChain} 
                    onChange={(e) => {
                        setSelectedChain(e.target.value);
                        setSelectedGroup('');
                    }}
                    className="chain-filter"
                >
                    <option value="">All Companies</option>
                    {chains.map(chain => (
                        <option key={chain.chainId} value={chain.chainId}>
                            {chain.companyName}
                        </option>
                    ))}
                </select>
            </div>

            <button 
                className="add-button"
                onClick={() => setShowAddForm(true)}
            >
                Add New Brand
            </button>

            {showAddForm && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Add New Brand</h2>
                        <form onSubmit={handleAddBrand}>
                            <div className="form-group">
                                <label>Brand Name</label>
                                <input
                                    type="text"
                                    name="brandName"
                                    value={formData.brandName}
                                    onChange={handleInputChange}
                                    placeholder="Enter brand name"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Company</label>
                                <select
                                    name="chainId"
                                    value={formData.chainId}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Select a company</option>
                                    {chains.map(chain => (
                                        <option key={chain.chainId} value={chain.chainId}>
                                            {chain.companyName}
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
                        <h2>Edit Brand</h2>
                        <form onSubmit={handleEditBrand}>
                            <div className="form-group">
                                <label>Brand Name</label>
                                <input
                                    type="text"
                                    name="brandName"
                                    value={formData.brandName}
                                    onChange={handleInputChange}
                                    placeholder="Enter brand name"
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Company</label>
                                <select
                                    name="chainId"
                                    value={formData.chainId}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Select a company</option>
                                    {chains.map(chain => (
                                        <option key={chain.chainId} value={chain.chainId}>
                                            {chain.companyName}
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

            <div className="brands-list">
                <table>
                    <thead>
                        <tr>
                            <th>Brand Name</th>
                            <th>Company</th>
                            <th>Group</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {brands.length === 0 ? (
                            <tr>
                                <td colSpan="5" style={{ textAlign: 'center' }}>No brands found</td>
                            </tr>
                        ) : (
                            brands.map((brand) => (
                                <tr key={brand.brandId}>
                                    <td>{brand.brandName}</td>
                                    <td>{brand.chain?.companyName}</td>
                                    <td>{brand.chain?.group?.groupName}</td>
                                    <td>{new Date(brand.createdAt).toLocaleDateString()}</td>
                                    <td>
                                        <button 
                                            className="edit-button"
                                            onClick={() => openEditForm(brand)}
                                        >
                                            Edit
                                        </button>
                                        <button 
                                            className="delete-button"
                                            onClick={() => handleDeleteBrand(brand.brandId)}
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

export default BrandDashboard; 