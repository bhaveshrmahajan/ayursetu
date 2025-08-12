import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    console.log('API Request:', config.method?.toUpperCase(), config.url);
    console.log('API Base URL:', config.baseURL);
    console.log('API Request Data:', config.data);
    
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error('API Request Error:', error);
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => {
    console.log('API Response:', response.status, response.config.url);
    console.log('API Response Data:', response.data);
    return response;
  },
  (error) => {
    console.error('API Response Error:', error.response?.status, error.response?.data);
    console.error('API Error URL:', error.config?.url);
    
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// User Service APIs
export const userAPI = {
  login: (credentials) => api.post('/api/users/login', credentials),
  register: (userData) => api.post('/api/users/register', userData),
  getProfile: (email) => api.get(`/api/users/${email}`),
  updateProfile: (email, userData) => api.put(`/api/users/${email}`, userData),
  forgotPassword: (email) => api.post('/api/users/forgot-password', null, { params: { email } }),
  resetPassword: (email, newPassword) => api.post('/api/users/reset-password', null, { params: { email, newPassword } }),
};

// Doctor Service APIs
export const doctorAPI = {
  getAllDoctors: () => api.get('/api/doctors'),
  getDoctorById: (id) => api.get(`/api/doctors/${id}`),
  getDoctorByEmail: (email) => api.get(`/api/doctors/email/${email}`),
  getDoctorsBySpecialization: (specialization) => api.get(`/api/doctors/specialization/${specialization}`),
  getDoctorsByCity: (city) => api.get(`/api/doctors/city/${city}`),
  getAvailableDoctors: () => api.get('/api/doctors/available'),
  getVerifiedDoctors: () => api.get('/api/doctors/verified'),
  searchDoctors: (specialization, city) => api.get('/api/doctors/search', { params: { specialization, city } }),
  getDoctorsByFeeRange: (minFee, maxFee) => api.get('/api/doctors/fee-range', { params: { minFee, maxFee } }),
  getAllSpecializations: () => api.get('/api/doctors/specializations'),
  getAllCities: () => api.get('/api/doctors/cities'),
  createDoctor: (doctorData) => api.post('/api/doctors', doctorData),
  updateDoctor: (id, doctorData) => api.put(`/api/doctors/${id}`, doctorData),
  deleteDoctor: (id) => api.delete(`/api/doctors/${id}`),
  updateAvailability: (id, isAvailable) => api.patch(`/api/doctors/${id}/availability`, null, { params: { isAvailable } }),
  updateVerificationStatus: (id, isVerified) => api.patch(`/api/doctors/${id}/verification`, null, { params: { isVerified } }),
};

// Consultation Service APIs
export const consultationAPI = {
  getAllConsultations: () => api.get('/api/consultations'),
  getConsultationById: (id) => api.get(`/api/consultations/${id}`),
  getConsultationsByUserId: (userId) => api.get(`/api/consultations/user/${userId}`),
  getConsultationsByDoctorId: (doctorId) => api.get(`/api/consultations/doctor/${doctorId}`),
  getConsultationsByStatus: (status) => api.get(`/api/consultations/status/${status}`),
  createConsultation: (consultationData) => api.post('/api/consultations', consultationData),
  updateConsultation: (id, consultationData) => api.put(`/api/consultations/${id}`, consultationData),
  deleteConsultation: (id) => api.delete(`/api/consultations/${id}`),
  updateConsultationStatus: (id, status) => api.patch(`/api/consultations/${id}/status`, null, { params: { status } }),
  updateConsultationDiagnosis: (id, diagnosis, prescription, notes) => 
    api.patch(`/api/consultations/${id}/diagnosis`, null, { params: { diagnosis, prescription, notes } }),
  getOverdueConsultations: () => api.get('/api/consultations/overdue'),
  generateMeetingLink: (id) => api.post(`/api/consultations/${id}/meeting-link`),
};

// Pharmacy Service APIs
export const pharmacyAPI = {
  getAllMedicines: () => api.get('/api/pharmacy/medicines'),
  getMedicineById: (id) => api.get(`/api/pharmacy/medicines/${id}`),
  createMedicine: (medicineData) => api.post('/api/pharmacy/medicines', medicineData),
  updateMedicine: (id, medicineData) => api.put(`/api/pharmacy/medicines/${id}`, medicineData),
  deleteMedicine: (id) => api.delete(`/api/pharmacy/medicines/${id}`),
  getMedicinesByCategory: (category) => api.get(`/api/pharmacy/medicines/category/${category}`),
  getAvailableMedicines: () => api.get('/api/pharmacy/medicines/available'),
  searchMedicines: (query) => api.get('/api/pharmacy/medicines/search', { params: { query } }),
  updateStock: (id, quantity) => api.put(`/api/pharmacy/medicines/${id}/stock`, null, { params: { quantity } }),
  getLowStockMedicines: () => api.get('/api/pharmacy/medicines/low-stock'),
  health: () => api.get('/api/pharmacy/health'),
};

// Notification Service APIs
export const notificationAPI = {
  sendEmail: (emailData) => api.post('/api/notifications/email', emailData),
  sendSMS: (smsData) => api.post('/api/notifications/sms', smsData),
  sendPushNotification: (pushData) => api.post('/api/notifications/push', pushData),
  getAllNotifications: () => api.get('/api/notifications'),
  getNotificationById: (id) => api.get(`/api/notifications/${id}`),
  createNotification: (notificationData) => api.post('/api/notifications', notificationData),
  updateNotification: (id, notificationData) => api.put(`/api/notifications/${id}`, notificationData),
  deleteNotification: (id) => api.delete(`/api/notifications/${id}`),
};

export default api;





