import axios from 'axios';

const API_URL = 'http://localhost:8080/api/invoices';

const invoiceAPI = {
    getAllInvoices: async () => {
        try {
            console.log('Making API call to:', API_URL);
            const response = await axios.get(API_URL);
            console.log('API Response status:', response.status);
            console.log('API Response headers:', response.headers);
            return response.data;
        } catch (error) {
            console.error('API Error:', {
                message: error.message,
                status: error.response?.status,
                data: error.response?.data
            });
            throw error;
        }
    },

    getInvoiceById: async (id) => {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    },

    getInvoiceByInvoiceNo: async (invoiceNo) => {
        const response = await axios.get(`${API_URL}/number/${invoiceNo}`);
        return response.data;
    },

    getInvoicesByEstimateId: async (estimateId) => {
        const response = await axios.get(`${API_URL}/estimate/${estimateId}`);
        return response.data;
    },

    getInvoicesByChainId: async (chainId) => {
        const response = await axios.get(`${API_URL}/chain/${chainId}`);
        return response.data;
    },

    searchInvoicesByCompanyName: async (companyName) => {
        const response = await axios.get(`${API_URL}/search?companyName=${companyName}`);
        return response.data;
    },

    createInvoice: async (invoiceData) => {
        const response = await axios.post(API_URL, invoiceData);
        return response.data;
    },

    updateInvoice: async (id, invoiceData) => {
        const response = await axios.put(`${API_URL}/${id}`, invoiceData);
        return response.data;
    },

    deleteInvoice: async (id) => {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.data;
    }
};

export default invoiceAPI; 