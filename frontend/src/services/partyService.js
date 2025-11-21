import api from './api';

export const partyService = {
  async create(partyData) {
    const response = await api.post('/party/create', partyData);
    return response.data;
  },

  async getAll() {
    const response = await api.get('/party/all');
    return response.data;
  }
};