import axiosInstance from '../axios.config';

export const dashboardAPI = {
  getSummary: () => axiosInstance.get('/dashboard/summary'),
  getRiskProfiles: () => axiosInstance.get('/dashboard/risk-profiles'),
  getOpenCases: () => axiosInstance.get('/dashboard/open-cases'),
  getRecentEvents: () => axiosInstance.get('/dashboard/events'),
  getDecisions: () => axiosInstance.get('/dashboard/decisions'),
  getActiveRules: () => axiosInstance.get('/dashboard/active-rules'),
};