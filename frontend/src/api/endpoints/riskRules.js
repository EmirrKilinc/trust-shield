import axiosInstance from '../axios.config';

export const riskRulesAPI = {
  getAllRules: () => axiosInstance.get('/risk-rules'),
  getActiveRules: () => axiosInstance.get('/risk-rules/active'),
  getRuleById: (id) => axiosInstance.get(`/risk-rules/${id}`),
  createRule: (data) => axiosInstance.post('/risk-rules', data),
  updateRule: (id, data) => axiosInstance.put(`/risk-rules/${id}`, data),
  activateRule: (id, active) => axiosInstance.put(`/risk-rules/${id}/${active}/activate`),
};