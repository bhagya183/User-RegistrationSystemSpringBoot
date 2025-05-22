import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

// Create axios instance with default config
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    }
});

// Add request interceptor to add auth token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Add response interceptor to handle errors
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Handle unauthorized access
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

// Auth API calls
export const authAPI = {
    login: async (credentials) => {
        try {
            const response = await api.post('/auth/login', credentials);
            return response;
        } catch (error) {
            console.error('Login error:', error.response?.data || error.message);
            throw error;
        }
    },
    register: (userData) => api.post('/auth/register', userData),
    forgotPassword: (email) => api.post('/auth/forgot-password', { email }),
    resetPassword: (data) => api.post('/auth/reset-password', data),
};

// User API calls
export const userAPI = {
    getProfile: () => api.get('/user/profile'),
    updateProfile: (data) => api.put('/user/profile', data),
};

// Admin API calls
export const adminAPI = {
    getUsers: () => api.get('/admin/users'),
    updateUser: (userId, data) => api.put(`/admin/users/${userId}`, data),
    deleteUser: (userId) => api.delete(`/admin/users/${userId}`),
};

// Sales API calls
export const salesAPI = {
    getSales: () => api.get('/sales'),
    createSale: (data) => api.post('/sales', data),
    updateSale: (saleId, data) => api.put(`/sales/${saleId}`, data),
};

export default api; 