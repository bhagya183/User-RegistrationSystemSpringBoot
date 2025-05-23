import React, { useState, useEffect } from 'react';
import estimateAPI from '../../services/estimateAPI';
import './EstimateManagement.css';

const EstimateManagement = () => {
    const [estimates, setEstimates] = useState([]);
    const [formData, setFormData] = useState({
        chainId: '',
        groupName: '',
        brandName: '',
        zoneName: '',
        service: '',
        quantity: '',
        costPerUnit: '',
        deliveryDate: '',
        deliveryDetails: ''
    });
    const [editingId, setEditingId] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        loadEstimates();
    }, []);

    const loadEstimates = async () => {
        try {
            console.log('Fetching estimates...');
            const data = await estimateAPI.getAllEstimates();
            console.log('Received estimates:', data);
            setEstimates(data);
        } catch (err) {
            console.error('Error loading estimates:', err);
            setError(err.response?.data?.message || 'Failed to load estimates. Please try again later.');
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        try {
            if (editingId) {
                await estimateAPI.updateEstimate(editingId, formData);
                setSuccess('Estimate updated successfully');
            } else {
                await estimateAPI.createEstimate(formData);
                setSuccess('Estimate created successfully');
            }
            resetForm();
            loadEstimates();
        } catch (err) {
            setError(err.response?.data?.message || 'Operation failed');
        }
    };

    const handleEdit = (estimate) => {
        setEditingId(estimate.estimateId);
        setFormData({
            chainId: estimate.chainId,
            groupName: estimate.groupName,
            brandName: estimate.brandName,
            zoneName: estimate.zoneName,
            service: estimate.service,
            quantity: estimate.quantity,
            costPerUnit: estimate.costPerUnit,
            deliveryDate: estimate.deliveryDate,
            deliveryDetails: estimate.deliveryDetails
        });
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this estimate?')) {
            try {
                await estimateAPI.deleteEstimate(id);
                setSuccess('Estimate deleted successfully');
                loadEstimates();
            } catch (err) {
                setError('Failed to delete estimate');
            }
        }
    };

    const resetForm = () => {
        setFormData({
            chainId: '',
            groupName: '',
            brandName: '',
            zoneName: '',
            service: '',
            quantity: '',
            costPerUnit: '',
            deliveryDate: '',
            deliveryDetails: ''
        });
        setEditingId(null);
    };

    return (
        <div className="estimate-management">
            <h2>{editingId ? 'Edit Estimate' : 'Create New Estimate'}</h2>
            
            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            <form onSubmit={handleSubmit} className="estimate-form">
                <div className="form-group">
                    <label>Chain ID:</label>
                    <input
                        type="number"
                        name="chainId"
                        value={formData.chainId}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Group Name:</label>
                    <input
                        type="text"
                        name="groupName"
                        value={formData.groupName}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Brand Name:</label>
                    <input
                        type="text"
                        name="brandName"
                        value={formData.brandName}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Zone Name:</label>
                    <input
                        type="text"
                        name="zoneName"
                        value={formData.zoneName}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Service:</label>
                    <input
                        type="text"
                        name="service"
                        value={formData.service}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Quantity:</label>
                    <input
                        type="number"
                        name="quantity"
                        value={formData.quantity}
                        onChange={handleInputChange}
                        required
                        min="1"
                    />
                </div>

                <div className="form-group">
                    <label>Cost per Unit:</label>
                    <input
                        type="number"
                        name="costPerUnit"
                        value={formData.costPerUnit}
                        onChange={handleInputChange}
                        required
                        min="0"
                        step="0.01"
                    />
                </div>

                <div className="form-group">
                    <label>Delivery Date:</label>
                    <input
                        type="date"
                        name="deliveryDate"
                        value={formData.deliveryDate}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Delivery Details:</label>
                    <textarea
                        name="deliveryDetails"
                        value={formData.deliveryDetails}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-buttons">
                    <button type="submit" className="submit-button">
                        {editingId ? 'Update' : 'Create'} Estimate
                    </button>
                    {editingId && (
                        <button type="button" onClick={resetForm} className="cancel-button">
                            Cancel
                        </button>
                    )}
                </div>
            </form>

            <div className="estimates-list">
                <h3>Existing Estimates</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Chain ID</th>
                            <th>Group</th>
                            <th>Brand</th>
                            <th>Zone</th>
                            <th>Service</th>
                            <th>Quantity</th>
                            <th>Cost/Unit</th>
                            <th>Total Cost</th>
                            <th>Delivery Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {estimates.map(estimate => (
                            <tr key={estimate.estimateId}>
                                <td>{estimate.estimateId}</td>
                                <td>{estimate.chainId}</td>
                                <td>{estimate.groupName}</td>
                                <td>{estimate.brandName}</td>
                                <td>{estimate.zoneName}</td>
                                <td>{estimate.service}</td>
                                <td>{estimate.quantity}</td>
                                <td>${estimate.costPerUnit}</td>
                                <td>${estimate.totalCost}</td>
                                <td>{new Date(estimate.deliveryDate).toLocaleDateString()}</td>
                                <td>
                                    <button
                                        onClick={() => handleEdit(estimate)}
                                        className="edit-button"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(estimate.estimateId)}
                                        className="delete-button"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default EstimateManagement; 