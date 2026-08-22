import { apiClient } from '@/services/apiClient';
import { LoginRequest, RegisterRequest, User } from '../schemas/auth';

export const authService = {
  async login(data: LoginRequest): Promise<User> {
    const response = await apiClient.post<User>('/api/auth/login', data);
    const user = response.data;
    if (user.token) {
      localStorage.setItem('token', user.token);
    }
    // Also save user info in localStorage for instant restore on refresh
    localStorage.setItem('user', JSON.stringify(user));
    return user;
  },

  async register(data: RegisterRequest): Promise<User> {
    const response = await apiClient.post<User>('/api/auth/register', data);
    return response.data;
  },

  async logout(): Promise<void> {
    try {
      await apiClient.post('/api/auth/logout');
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    }
  },

  async getMe(): Promise<User> {
    const response = await apiClient.get<User>('/api/users/me');
    const user = response.data;
    // Keep local storage in sync
    localStorage.setItem('user', JSON.stringify(user));
    return user;
  },
};
