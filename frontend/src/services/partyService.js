import api from './api';

export const partyService = {
  async create(partyData) {
    const response = await api.post('/party/create', partyData);
    return response.data;
  },

  async getAll() {
    const response = await api.get('/party/all');
    return response.data;
  },

  async getGallery() {
    const response = await api.get('/party/gallery');
    return response.data;
  },
  
  async updateStatus(id, status) {
    const response = await api.patch(`/party/${id}/status`, status, {
        headers: { 'Content-Type': 'text/plain' } 
    });
    return response.data;
  }
};