import axiosInstance from '../axios.config';

export const eventsAPI = {
  getAllEvents: () => axiosInstance.get('/events'),
  getEventById: (id) => axiosInstance.get(`/events/${id}`),
  createEvent: (data) => axiosInstance.post('/events', data),
  updateEvent: (id, data) => axiosInstance.put(`/events/${id}`, data),
};