import axios from 'axios';

const API_URL = 'http://localhost:8080/api/estimates';

const estimateAPI = {
    getAllEstimates: async () => {
        const response = await axios.get(API_URL);
        return response.data;
    },

    getEstimateById: async (id) => {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    },

    getEstimatesByChainId: async (chainId) => {
        const response = await axios.get(`${API_URL}/chain/${chainId}`);
        return response.data;
    },

    createEstimate: async (estimateData) => {
        const response = await axios.post(API_URL, estimateData);
        return response.data;
    },

    updateEstimate: async (id, estimateData) => {
        const response = await axios.put(`${API_URL}/${id}`, estimateData);
        return response.data;
    },

    deleteEstimate: async (id) => {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.data;
    }
};

export default estimateAPI; 