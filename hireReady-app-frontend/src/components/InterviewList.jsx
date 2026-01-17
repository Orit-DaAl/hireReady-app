import { Card, CardContent, Grid, Typography, Chip, Box, Divider, CircularProgress } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getInterviews } from "../services/api";
import { Work, Business, CalendarToday, CheckCircle as CheckCircleIcon } from "@mui/icons-material";

const InterviewList = () => {
    const [interviews, setInterviews] = useState([]);
    const [loading, setLoading] = useState(true); 
    const navigate = useNavigate();

    const fetchInterviews = async () => {
        try {
            const response = await getInterviews();
            setInterviews(response.data);
        } catch (error) {
            console.error("Error fetching interviews:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchInterviews();
    }, []);

    useEffect(() => {
        const hasInProgress = interviews.some(i => i.status === 'PREP_IN_PROGRESS');
        
        if (hasInProgress) {
            const interval = setInterval(() => {
                fetchInterviews(); 
            }, 5000);
            
            return () => clearInterval(interval);
        }
    }, [interviews]);

    if (loading && interviews.length === 0) {
        return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}><CircularProgress /></Box>;
    }

    return (
        <Grid container spacing={3} sx={{ py: 2 }}>
            {interviews.length === 0 ? (
                <Grid item xs={12}>
                    <Typography variant="body1" color="text.secondary" sx={{ textAlign: 'center', mt: 4 }}>
                        No interviews found. Start by adding your first interview above!
                    </Typography>
                </Grid>
            ) : (
                interviews.map((interview) => (
                    <Grid 
                        item 
                        key={interview.id} 
                        xs={12} sm={6} md={4}
                    >
                        <Card 
                            elevation={2}
                            sx={{ 
                                cursor: 'pointer', 
                                height: '100%',
                                transition: '0.3s',
                                '&:hover': { transform: 'translateY(-5px)', boxShadow: 6 },
                                borderRadius: 3,
                                display: 'flex',
                                flexDirection: 'column'
                            }}
                            onClick={() => navigate(`/interviews/${interview.id}`)}
                        >
                            <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2, alignItems: 'center' }}>
                                    <Chip 
                                        label={interview.type} 
                                        size="small" 
                                        color="primary" 
                                        variant="outlined" 
                                    />
                                    <Chip 
                                        label={interview.status === 'PREP_IN_PROGRESS' ? 'AI Analyzing...' : 'Ready'}
                                        color={interview.status === 'PREP_IN_PROGRESS' ? 'warning' : 'success'}
                                        variant={interview.status === 'PREP_IN_PROGRESS' ? 'outlined' : 'filled'}
                                        size="small"
                                        icon={interview.status === 'PREP_IN_PROGRESS' ? 
                                            <CircularProgress size={14} color="inherit" /> : 
                                            <CheckCircleIcon style={{ fontSize: '16px' }} />
                                        }
                                    />
                                </Box>

                                <Typography variant='h6' sx={{ fontWeight: 'bold', mb: 1, display: 'flex', alignItems: 'center', gap: 1 }}>
                                    <Business fontSize="small" color="action" /> {interview.companyName}
                                </Typography>

                                <Typography variant="subtitle1" color="text.secondary" sx={{ mb: 2, display: 'flex', alignItems: 'center', gap: 1 }}>
                                    <Work fontSize="small" color="action" /> {interview.jobTitle}
                                </Typography>

                                { }
                                <Box sx={{ mt: 'auto' }}>
                                    <Divider sx={{ my: 1.5 }} />
                                    <Typography 
                                        variant="caption" 
                                        color="text.secondary" 
                                        sx={{ 
                                            display: 'flex', 
                                            alignItems: 'center', 
                                            gap: 1,
                                            fontWeight: 500 
                                        }}
                                    >
                                        <CalendarToday sx={{ fontSize: 14 }} /> 
                                        {interview.interviewDate ? 
                                            new Date(interview.interviewDate).toLocaleString('he-IL', {
                                                day: '2-digit',
                                                month: '2-digit',
                                                year: 'numeric',
                                                hour: '2-digit',
                                                minute: '2-digit'
                                            }) : 
                                            'Date not set'
                                        }
                                    </Typography>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))
            )}
        </Grid>
    );
};

export default InterviewList;