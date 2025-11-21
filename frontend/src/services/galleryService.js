import api from './api';

export const galleryService = {
  async getAll() {
    const response = await api.get('/gallery/all');
    return response.data;
  },

  async create(galleryData) {
    const response = await api.post('/gallery/create', galleryData);
    return response.data;
  }
};