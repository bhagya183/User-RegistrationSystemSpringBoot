import axios from 'axios';

const API_URL = 'http://localhost:8080/api/brands';

export const brandAPI = {
    getAllBrands: () => axios.get(API_URL),
    getBrandById: (brandId) => axios.get(`${API_URL}/${brandId}`),
    getBrandsByChain: (chainId) => axios.get(`${API_URL}/chain/${chainId}`),
    getBrandsByGroup: (groupId) => axios.get(`${API_URL}/group/${groupId}`),
    createBrand: (brandData) => axios.post(API_URL, brandData),
    updateBrand: (brandId, brandData) => axios.put(`${API_URL}/${brandId}`, brandData),
    deleteBrand: (brandId) => axios.delete(`${API_URL}/${brandId}`)
}; 