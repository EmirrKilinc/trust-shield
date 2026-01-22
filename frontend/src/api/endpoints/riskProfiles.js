import axiosInstance from '../axios.config';

export const riskProfilesAPI = {
  getAllProfiles: () => axiosInstance.get('/risk-profiles'),
  getProfileById: (id) => axiosInstance.get(`/risk-profiles/${id}`),
  createProfile: (data) => axiosInstance.post('/risk-profiles', data),
  updateProfile: (id, data) => axiosInstance.put(`/risk-profiles/${id}`, data),
};