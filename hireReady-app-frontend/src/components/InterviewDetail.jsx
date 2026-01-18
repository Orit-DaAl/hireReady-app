import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { 
  Box, Card, CardContent, Divider, Typography, CircularProgress, 
  Grid, Paper, Chip, Stack, Button, Accordion, AccordionSummary, AccordionDetails 
} from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import PsychologyIcon from '@mui/icons-material/Psychology';
import BusinessIcon from '@mui/icons-material/Business';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import TargetIcon from '@mui/icons-material/TrackChanges';
import GroupsIcon from '@mui/icons-material/Groups';
import { getInterviewsDetail } from "../services/api";
import ForumIcon from '@mui/icons-material/Forum';

const InterviewDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [data, setData] = useState(null); 
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let intervalId;

    const fetchData = async () => {
      try {
        const response = await getInterviewsDetail(id);
        
        // general Analysis
        if (response.data && (response.data.analysis || response.data.generalAnalysis)) {
          setData(response.data);
          setLoading(false);
          if (intervalId) {
            clearInterval(intervalId);
            intervalId = null;
          }
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          console.log("AI Prep not ready, retrying...");
          if (!intervalId) {
            intervalId = setInterval(fetchData, 3000);
          }
        } else {
          console.error("Error fetching detail:", error);
        }
      }
    };

    fetchData();
    return () => { if (intervalId) clearInterval(intervalId); };
  }, [id]);

  if (!data && loading) {
    return (
      <Box sx={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', height: '60vh', gap: 2 }}>
        <CircularProgress />
        <Typography color="textSecondary">Initializing AI Analysis...</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ maxWidth: 1000, mx: 'auto', p: { xs: 2, md: 4 } }}>
      <Button 
        startIcon={<ArrowBackIcon />} 
        onClick={() => navigate(-1)} 
        sx={{ mb: 3, textTransform: 'none' }}
      >
        Back to Dashboard
      </Button>

      {/* Main Header */}
      <Paper elevation={0} sx={{ p: 4, mb: 4, borderRadius: 4, bgcolor: '#1a237e', color: 'white' }}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} md={8}>
            <Typography variant="overline" sx={{ opacity: 0.8, letterSpacing: 1.5 }}>Interview Preparation Kit</Typography>
            <Typography variant="h3" sx={{ fontWeight: 'bold', mb: 1 }}>
              {data?.jobTitle || "Position"}
            </Typography>
            <Stack direction="row" spacing={1} alignItems="center">
              <BusinessIcon sx={{ fontSize: 20, opacity: 0.9 }} />
              <Typography variant="h5" sx={{ opacity: 0.9 }}>{data?.companyName || "Company"}</Typography>
            </Stack>
          </Grid>
          <Grid item xs={12} md={4} sx={{ textAlign: { md: 'right' } }}>
             <Chip 
              label={`Status: Ready`} 
              sx={{ bgcolor: 'rgba(255,255,255,0.2)', color: 'white', fontWeight: 'bold' }} 
            />
          </Grid>
        </Grid>
      </Paper>

      <Typography variant="h5" sx={{ mb: 3, fontWeight: 'bold', display: 'flex', alignItems: 'center', gap: 1.5 }}>
        <PsychologyIcon sx={{ color: '#1a237e', fontSize: 32 }} /> AI Coach Insights
      </Typography>

      <Stack spacing={3}>
        
        {/* Role & Strategy Analysis Section */}
        <Card sx={{ borderRadius: 4, borderLeft: '6px solid #1a237e', boxShadow: 3 }}>
          <CardContent sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', fontWeight: 'bold', color: '#1a237e' }}>
              <TargetIcon sx={{ mr: 1.5 }} /> Role & Strategy Analysis
            </Typography>
            
            {/* General Analysis */}
            <Typography variant="body1" sx={{ lineHeight: 1.8, color: '#37474f', whiteSpace: 'pre-line' }}>
              {data?.analysis?.roleOverview || data?.generalAnalysis}
            </Typography>
            
            {/* company Culture if exists */}
            {data?.analysis?.companyCulture && (
              <>
                <Divider sx={{ my: 3 }} />
                <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', fontWeight: 'bold', color: '#1a237e' }}>
                  <GroupsIcon sx={{ mr: 1.5 }} /> Company Culture
                </Typography>
                <Typography variant="body1" sx={{ lineHeight: 1.8, color: '#37474f', fontStyle: 'italic' }}>
                  {data.analysis.companyCulture}
                </Typography>
              </>
            )}
          </CardContent>
        </Card>

        {/* Predicted Questions Section - Closed by default */}
        <Accordion sx={{ borderRadius: '16px !important', border: '1px solid #e0e0e0', boxShadow: 'none' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', fontSize: '1.1rem' }}>
              <QuestionAnswerIcon sx={{ mr: 1.5, color: '#ff9800' }} /> Predicted Interview Questions
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Stack spacing={1.5}>
              {data?.predictedQuestions?.map((item, i) => (
                <Paper key={i} variant="outlined" sx={{ p: 2, bgcolor: '#fffde7', border: '1px solid #fff59d', borderRadius: 2 }}>
                  <Typography variant="body1" sx={{ fontWeight: '500' }}>{item}</Typography>
                </Paper>
              ))}
            </Stack>
          </AccordionDetails>
        </Accordion>

        {/* Community Questions Section */}
        <Accordion sx={{ borderRadius: '16px !important', border: '1px solid #e0e0e0', boxShadow: 'none' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', fontSize: '1.1rem' }}>
              <ForumIcon sx={{ mr: 1.5, color: '#2196f3' }} /> Crowdsourced Questions (Glassdoor, Reddit, etc.)
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            {data?.communityQuestions && data.communityQuestions.length > 0 ? (
              <Box sx={{ maxHeight: 400, overflowY: 'auto', pr: 1 }}>
                <Stack spacing={1.5}>
                  {data.communityQuestions.map((item, i) => (
                    <Paper key={i} variant="outlined" sx={{ p: 2, bgcolor: '#e3f2fd', border: '1px solid #bbdefb', borderRadius: 2 }}>
                      <Typography variant="body2" sx={{ fontWeight: '500', color: '#1565c0' }}>{item}</Typography>
                    </Paper>
                  ))}
                </Stack>
              </Box>
            ) : (
              <Typography variant="body2" sx={{ p: 2, fontStyle: 'italic', color: 'text.secondary', textAlign: 'center' }}>
                No specific community-reported questions found for this company yet.
              </Typography>
            )}
          </AccordionDetails>
        </Accordion>


        {/* Focus Areas Section */}
        <Accordion sx={{ borderRadius: '16px !important', border: '1px solid #e0e0e0', boxShadow: 'none' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', fontSize: '1.1rem' }}>
              <TargetIcon sx={{ mr: 1.5, color: '#4caf50' }} /> Key Focus Areas & Technologies
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
          <Stack spacing={1.5}>
            {data?.focusAreas?.map((item, i) => (
              <Paper 
                key={i} 
                variant="outlined" 
                sx={{ 
                  p: 2, 
                  bgcolor: '#e8f5e9', 
                  border: '1px solid #c8e6c9', 
                  borderRadius: 2,
               
                  whiteSpace: 'normal', 
                  wordBreak: 'break-word' 
                }}
              >
                <Typography 
                  variant="body2" 
                  sx={{ 
                    color: '#2e7d32', 
                    fontWeight: '600',
                    lineHeight: 1.5 
                  }}
                >
                  {item}
                </Typography>
              </Paper>
            ))}
          </Stack>
        </AccordionDetails>
        </Accordion>

        {/* Expert Tips Section */}
        <Accordion sx={{ borderRadius: '16px !important', border: '1px solid #e0e0e0', boxShadow: 'none' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', fontSize: '1.1rem' }}>
              <LightbulbIcon sx={{ mr: 1.5, color: '#f44336' }} /> Expert Tips & Tricks
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Stack spacing={1.5}>
              {data?.specificTips?.map((item, i) => (
                <Box key={i} sx={{ display: 'flex', gap: 2, p: 1, alignItems: 'flex-start' }}>
                  <Typography color="error" sx={{ fontWeight: 'bold' }}>•</Typography>
                  <Typography variant="body1">{item}</Typography>
                </Box>
              ))}
            </Stack>
          </AccordionDetails>
        </Accordion>
      </Stack>
    </Box>
  );
};

export default InterviewDetail;