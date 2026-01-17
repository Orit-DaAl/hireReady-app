import axios from "axios";
const API_URL = 'http://localhost:9090/api';

const api = axios.create({
    baseURL:API_URL
});

api.interceptors.request.use((config) => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    if (userId) {
        config.headers['X-User-ID'] = userId;
    }
    return config;
}
);

export const getInterviews = () => api.get('/interviews');
export const addInterview = (interview) => api.post('/interviews', interview);
export const getInterviewsDetail = (id) => api.get(`/interviewPreparations/interview/${id}`);