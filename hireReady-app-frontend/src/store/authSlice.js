import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null
  },
  reducers: {
    setCredentials: (state, action) => {
      const { user, token } = action.payload;

      state.user = user;
      state.token = token;
      state.userId = user?.sub || null;

      if (token && user) {
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('userId', user.sub);
      } else {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('userId');
      }
    },
    logout: (state) => {
      state.user = null;
      state.token = null;
      state.userId = null;
      
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('userId');
    },
  },
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;