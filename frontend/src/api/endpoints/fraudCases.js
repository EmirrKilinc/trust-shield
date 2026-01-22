import axiosInstance from '../axios.config';

export const fraudCasesAPI = {
  getAllCases: () => axiosInstance.get('/fraud-cases'),
  getOpenCases: () => axiosInstance.get('/fraud-cases/open'),
  getCaseActions: (id) => axiosInstance.get(`/fraud-cases/${id}/actions`),
  openCase: (data) => axiosInstance.post('/fraud-cases', data),
  updateStatus: (id, data) => axiosInstance.put(`/fraud-cases/${id}/status`, data),
  addNote: (id, data) => axiosInstance.put(`/fraud-cases/${id}/notes`, data),
  closeCase: (id, data) => axiosInstance.put(`/fraud-cases/${id}/close`, data),
};