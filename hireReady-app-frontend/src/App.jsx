import React, { useContext, useEffect } from "react";
import { 
  Box, 
  Button, 
  Typography, 
  AppBar, 
  Toolbar, 
  Container, 
  CssBaseline,
  Paper 
} from "@mui/material";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom"; // תיקון קטן ב-import
import { setCredentials } from "./store/authSlice";

// Components
import InterviewForm from "./components/InterviewForm";
import InterviewList from "./components/InterviewList";
import InterviewDetail from "./components/InterviewDetail";

const InterviewsPage = () => {
  return (
    <Box sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 3, mb: 4, borderRadius: 2 }}>
        <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold', color: '#1976d2' }}>
          Prepare for Your Next Interview
        </Typography>
        <InterviewForm onInterviewAdded={() => window.location.reload()} />
      </Paper>
      
      <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold', mb: 2 }}>
        My Interview History
      </Typography>
      <InterviewList />
    </Box>
  );
};

function App() {
  const { token, tokenData, logIn, logOut } = useContext(AuthContext);
  const dispatch = useDispatch();

  useEffect(() => {
  if (token && tokenData) {
    dispatch(setCredentials({ token, user: tokenData }));
  } else if (!token) {
    dispatch(setCredentials({ token: null, user: null }));
  }
}, [token, tokenData, dispatch]);

 const handleLogout = () => {
  dispatch(setCredentials({ token: null, user: null }));
  localStorage.clear();
  sessionStorage.clear();
  logOut(); 
};

  return (
    <Router>
      <CssBaseline /> 
      
      {!token ? (
        <Box
          sx={{
            height: "100vh",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            background: "linear-gradient(135deg, #0f2027 0%, #203a43 50%, #2c5364 100%)", // צבעים יותר "קרייריסטים"
            color: "white",
            textAlign: "center",
            p: 3
          }}
        >
          <Paper elevation={6} sx={{ p: 5, borderRadius: 4, maxWidth: 500, textAlign: 'center' }}>
            <Typography variant="h3" gutterBottom sx={{ fontWeight: 'bold', color: '#1976d2' }}>
              HireReady
            </Typography>
            <Typography variant="h6" sx={{ mb: 4, color: '#555' }}>
              Master your next job interview with AI-powered insights and preparation.
            </Typography>
            <Button 
              variant="contained" 
              size="large" 
              onClick={() => logIn()} 
              sx={{ px: 8, py: 1.5, fontSize: '1.1rem', borderRadius: 10, textTransform: 'none' }}
            >
              Sign In to Start Preparing
            </Button>
          </Paper>
        </Box>
      ) : (
        <Box sx={{ flexGrow: 1, backgroundColor: '#f0f2f5', minHeight: '100vh' }}>
          <AppBar position="sticky" elevation={1} sx={{ backgroundColor: '#ffffff', color: '#333' }}>
            <Toolbar>
              <Typography variant="h5" component="div" sx={{ flexGrow: 1, fontWeight: 'bold', color: '#1976d2' }}>
                HireReady
              </Typography>
              <Typography variant="body1" sx={{ mr: 2, color: '#666' }}>
                Hello, <strong>{tokenData?.preferred_username || 'Candidate'}</strong>
              </Typography>
              <Button 
                color="primary" 
                variant="outlined" 
                onClick={handleLogout} 
                sx={{ borderRadius: 2, textTransform: 'none' }}
              >
                Logout
              </Button>
            </Toolbar>
          </AppBar>

          <Container maxWidth="lg" sx={{ pt: 2, pb: 5 }}>
            <Routes>
              {/* */}
              <Route path="/interviews" element={<InterviewsPage />} />
              <Route path="/interviews/:id" element={<InterviewDetail />} />
              <Route path="/" element={<Navigate to="/interviews" replace />} />
            </Routes>
          </Container>
        </Box>
      )}
    </Router>
  );
}

export default App;