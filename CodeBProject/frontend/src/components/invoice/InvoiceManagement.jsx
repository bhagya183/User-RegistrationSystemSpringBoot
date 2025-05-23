import React, { useState, useEffect } from 'react';
import invoiceAPI from '../../services/invoiceAPI';
import './InvoiceManagement.css';

const InvoiceManagement = () => {
    const [invoices, setInvoices] = useState([]);
    const [formData, setFormData] = useState({
        estimateId: '',
        chainId: '',
        serviceDetails: '',
        quantity: '',
        costPerQuantity: '',
        amountPayable: '',
        balance: '',
        dateOfPayment: '',
        dateOfService: '',
        deliveryDetails: '',
        emailId: ''
    });
    const [editingId, setEditingId] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        loadInvoices();
    }, []);

    const loadInvoices = async () => {
        try {
            console.log('Fetching invoices...');
            const response = await invoiceAPI.getAllInvoices();
            console.log('API Response:', response);
            if (Array.isArray(response)) {
                console.log('Received invoices:', response);
                setInvoices(response);
            } else {
                console.error('Invalid response format:', response);
                setError('Invalid response format from server');
            }
        } catch (err) {
            console.error('Error loading invoices:', err);
            console.error('Error details:', {
                message: err.message,
                response: err.response?.data,
                status: err.response?.status
            });
            setError(err.response?.data?.message || 'Failed to load invoices. Please try again later.');
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
                await invoiceAPI.updateInvoice(editingId, formData);
                setSuccess('Invoice updated successfully');
            } else {
                await invoiceAPI.createInvoice(formData);
                setSuccess('Invoice created successfully');
            }
            resetForm();
            loadInvoices();
        } catch (err) {
            setError(err.response?.data?.message || 'Operation failed');
        }
    };

    const handleEdit = (invoice) => {
        setEditingId(invoice.id);
        setFormData({
            estimateId: invoice.estimateId,
            chainId: invoice.chainId,
            serviceDetails: invoice.serviceDetails,
            quantity: invoice.quantity,
            costPerQuantity: invoice.costPerQuantity,
            amountPayable: invoice.amountPayable,
            balance: invoice.balance,
            dateOfPayment: invoice.dateOfPayment,
            dateOfService: invoice.dateOfService,
            deliveryDetails: invoice.deliveryDetails,
            emailId: invoice.emailId
        });
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this invoice?')) {
            try {
                await invoiceAPI.deleteInvoice(id);
                setSuccess('Invoice deleted successfully');
                loadInvoices();
            } catch (err) {
                setError('Failed to delete invoice');
            }
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const data = await invoiceAPI.searchInvoicesByCompanyName(searchTerm);
            setInvoices(data);
        } catch (err) {
            setError('Failed to search invoices');
        }
    };

    const resetForm = () => {
        setFormData({
            estimateId: '',
            chainId: '',
            serviceDetails: '',
            quantity: '',
            costPerQuantity: '',
            amountPayable: '',
            balance: '',
            dateOfPayment: '',
            dateOfService: '',
            deliveryDetails: '',
            emailId: ''
        });
        setEditingId(null);
    };

    return (
        <div className="invoice-management">
            <h2>{editingId ? 'Edit Invoice' : 'Create New Invoice'}</h2>
            
            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            <form onSubmit={handleSubmit} className="invoice-form">
                <div className="form-group">
                    <label>Estimate ID:</label>
                    <input
                        type="number"
                        name="estimateId"
                        value={formData.estimateId}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Chain ID:</label>
                    <input
                        type="number"
                        name="chainId"
                        value={formData.chainId}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Service Details:</label>
                    <input
                        type="text"
                        name="serviceDetails"
                        value={formData.serviceDetails}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
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
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Cost per Quantity:</label>
                    <input
                        type="number"
                        name="costPerQuantity"
                        value={formData.costPerQuantity}
                        onChange={handleInputChange}
                        required
                        min="0"
                        step="0.01"
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Amount Payable:</label>
                    <input
                        type="number"
                        name="amountPayable"
                        value={formData.amountPayable}
                        onChange={handleInputChange}
                        required
                        min="0"
                        step="0.01"
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Balance:</label>
                    <input
                        type="number"
                        name="balance"
                        value={formData.balance}
                        onChange={handleInputChange}
                        min="0"
                        step="0.01"
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Date of Payment:</label>
                    <input
                        type="datetime-local"
                        name="dateOfPayment"
                        value={formData.dateOfPayment}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Date of Service:</label>
                    <input
                        type="date"
                        name="dateOfService"
                        value={formData.dateOfService}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Delivery Details:</label>
                    <textarea
                        name="deliveryDetails"
                        value={formData.deliveryDetails}
                        onChange={handleInputChange}
                        required
                        disabled={editingId !== null}
                    />
                </div>

                <div className="form-group">
                    <label>Email ID:</label>
                    <input
                        type="email"
                        name="emailId"
                        value={formData.emailId}
                        onChange={handleInputChange}
                        required
                    />
                </div>

                <div className="form-buttons">
                    <button type="submit" className="submit-button">
                        {editingId ? 'Update' : 'Create'} Invoice
                    </button>
                    {editingId && (
                        <button type="button" onClick={resetForm} className="cancel-button">
                            Cancel
                        </button>
                    )}
                </div>
            </form>

            <div className="search-section">
                <form onSubmit={handleSearch}>
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search by company name..."
                    />
                    <button type="submit">Search</button>
                </form>
            </div>

            <div className="invoices-list">
                <h3>Existing Invoices</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Invoice No</th>
                            <th>Estimate ID</th>
                            <th>Company</th>
                            <th>Service</th>
                            <th>Quantity</th>
                            <th>Cost/Unit</th>
                            <th>Total</th>
                            <th>Balance</th>
                            <th>Payment Date</th>
                            <th>Service Date</th>
                            <th>Email</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {invoices.map(invoice => (
                            <tr key={invoice.id}>
                                <td>{invoice.invoiceNo}</td>
                                <td>{invoice.estimateId}</td>
                                <td>{invoice.companyName}</td>
                                <td>{invoice.serviceDetails}</td>
                                <td>{invoice.quantity}</td>
                                <td>${invoice.costPerQuantity}</td>
                                <td>${invoice.amountPayable}</td>
                                <td>${invoice.balance || 0}</td>
                                <td>{new Date(invoice.dateOfPayment).toLocaleString()}</td>
                                <td>{new Date(invoice.dateOfService).toLocaleDateString()}</td>
                                <td>{invoice.emailId}</td>
                                <td>
                                    <button
                                        onClick={() => handleEdit(invoice)}
                                        className="edit-button"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(invoice.id)}
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

export default InvoiceManagement; 