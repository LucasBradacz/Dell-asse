import api from './api';

export const partyService = {
  async create(partyData) {
    const response = await api.post('/party/create', partyData);
    return response.data;
  },
};

