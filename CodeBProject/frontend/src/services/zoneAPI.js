import axios from 'axios';

const API_URL = 'http://localhost:8080/api/zones';

export const zoneAPI = {
    getAllZones: () => axios.get(API_URL),
    getZonesByBrand: (brandId) => axios.get(`${API_URL}/brand/${brandId}`),
    getZonesByChain: (chainId) => axios.get(`${API_URL}/chain/${chainId}`),
    getZonesByGroup: (groupId) => axios.get(`${API_URL}/group/${groupId}`),
    createZone: (zoneData) => axios.post(API_URL, zoneData),
    updateZone: (zoneId, zoneData) => axios.put(`${API_URL}/${zoneId}`, zoneData),
    deleteZone: (zoneId) => axios.delete(`${API_URL}/${zoneId}`)
}; 