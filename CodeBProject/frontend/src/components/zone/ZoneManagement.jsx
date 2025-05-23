import React, { useState, useEffect } from 'react';
import { zoneAPI } from '../../services/zoneAPI';
import { brandAPI } from '../../services/brandAPI';
import './ZoneManagement.css';

const ZoneManagement = () => {
    const [zones, setZones] = useState([]);
    const [brands, setBrands] = useState([]);
    const [selectedBrand, setSelectedBrand] = useState('');
    const [formData, setFormData] = useState({
        zoneName: '',
        brandId: ''
    });
    const [editingZone, setEditingZone] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        loadBrands();
        loadZones();
    }, []);

    const loadBrands = async () => {
        try {
            const response = await brandAPI.getAllBrands();
            setBrands(response.data);
        } catch (err) {
            setError('Failed to load brands');
        }
    };

    const loadZones = async () => {
        try {
            const response = selectedBrand
                ? await zoneAPI.getZonesByBrand(selectedBrand)
                : await zoneAPI.getAllZones();
            setZones(response.data);
        } catch (err) {
            setError('Failed to load zones');
        }
    };

    const handleBrandChange = (e) => {
        const brandId = e.target.value;
        setSelectedBrand(brandId);
        setFormData({ ...formData, brandId });
        loadZones();
    };

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        try {
            if (editingZone) {
                await zoneAPI.updateZone(editingZone.zoneId, formData);
                setSuccess('Zone updated successfully');
            } else {
                await zoneAPI.createZone(formData);
                setSuccess('Zone created successfully');
            }
            setFormData({ zoneName: '', brandId: selectedBrand });
            setEditingZone(null);
            loadZones();
        } catch (err) {
            setError(err.response?.data?.message || 'Operation failed');
        }
    };

    const handleEdit = (zone) => {
        setEditingZone(zone);
        setFormData({
            zoneName: zone.zoneName,
            brandId: zone.brandId
        });
    };

    const handleDelete = async (zoneId) => {
        if (window.confirm('Are you sure you want to delete this zone?')) {
            try {
                await zoneAPI.deleteZone(zoneId);
                setSuccess('Zone deleted successfully');
                loadZones();
            } catch (err) {
                setError('Failed to delete zone');
            }
        }
    };

    return (
        <div className="zone-management">
            <h2>Zone Management</h2>
            
            <div className="zone-filters">
                <select value={selectedBrand} onChange={handleBrandChange}>
                    <option value="">All Brands</option>
                    {brands.map(brand => (
                        <option key={brand.brandId} value={brand.brandId}>
                            {brand.brandName}
                        </option>
                    ))}
                </select>
            </div>

            <form onSubmit={handleSubmit} className="zone-form">
                <input
                    type="text"
                    name="zoneName"
                    value={formData.zoneName}
                    onChange={handleInputChange}
                    placeholder="Zone Name"
                    required
                />
                <select
                    name="brandId"
                    value={formData.brandId}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Select Brand</option>
                    {brands.map(brand => (
                        <option key={brand.brandId} value={brand.brandId}>
                            {brand.brandName}
                        </option>
                    ))}
                </select>
                <button type="submit">
                    {editingZone ? 'Update Zone' : 'Create Zone'}
                </button>
                {editingZone && (
                    <button type="button" onClick={() => {
                        setEditingZone(null);
                        setFormData({ zoneName: '', brandId: selectedBrand });
                    }}>
                        Cancel Edit
                    </button>
                )}
            </form>

            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            <div className="zone-list">
                <table>
                    <thead>
                        <tr>
                            <th>Zone Name</th>
                            <th>Brand</th>
                            <th>Chain</th>
                            <th>Group</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {zones.map(zone => (
                            <tr key={zone.zoneId}>
                                <td>{zone.zoneName}</td>
                                <td>{zone.brandName}</td>
                                <td>{zone.chainName}</td>
                                <td>{zone.groupName}</td>
                                <td>
                                    <button onClick={() => handleEdit(zone)}>Edit</button>
                                    <button onClick={() => handleDelete(zone.zoneId)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ZoneManagement; 