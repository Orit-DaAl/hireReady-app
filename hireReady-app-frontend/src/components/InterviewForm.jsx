import React, { useState } from "react";
import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
  Grid,
  Divider,
  InputAdornment
} from "@mui/material";
import { Business, Work, Link, Description, Category , Event} from "@mui/icons-material";
import { addInterview } from "../services/api"; 

const InterviewForm = ({ onInterviewAdded }) => {
  const [interview, setInterview] = useState({
    companyName: "",
    jobTitle: "",
    type: "TECHNICAL", 
    jobDescription: "",
    interviewDate: ""
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
    
      await addInterview(interview);
      
      if (onInterviewAdded) {
        onInterviewAdded();
      }

      setInterview({
        companyName: "",
        jobTitle: "",
        type: "TECHNICAL",
        jobUrl: "",
        jobDescription: ""
      });
      
    } catch (error) {
      console.error("Error adding interview:", error);
      alert("Failed to schedule interview preparation. Please try again.");
    }
  };

  return (
    <Box 
      component="form" 
      onSubmit={handleSubmit} 
      sx={{ 
        p: 1,
      }}
    >
      <Grid container spacing={3}>
        {/* Job Title */}
        <Grid item xs={12} md={6}>
          <TextField
            fullWidth
            label="Job Title"
            placeholder="e.g. Backend Developer"
            value={interview.jobTitle}
            onChange={(e) => setInterview({ ...interview, jobTitle: e.target.value })}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Work color="action" />
                </InputAdornment>
              ),
            }}
            required
          />
        </Grid>

        {/* Company Name */}
        <Grid item xs={12} md={6}>
          <TextField
            fullWidth
            label="Company Name"
            placeholder="e.g. Google"
            value={interview.companyName}
            onChange={(e) => setInterview({ ...interview, companyName: e.target.value })}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Business color="action" />
                </InputAdornment>
              ),
            }}
            required
          />
        </Grid>

        {/* Interview Type*/}
        <Grid item xs={12} md={6}>
          <FormControl fullWidth>
            <InputLabel id="type-label">Interview Type</InputLabel>
            <Select
              labelId="type-label"
              value={interview.type}
              label="Interview Type"
              onChange={(e) => setInterview({ ...interview, type: e.target.value })}
              startAdornment={<Category sx={{ mr: 1, ml: -0.5 }} color="action" />}
            >
              <MenuItem value="TECHNICAL">Technical Interview</MenuItem>
              <MenuItem value="DESIGN">Design Interview</MenuItem>
              <MenuItem value="HR">HR / Behavioral</MenuItem>
              <MenuItem value="SCREENING">Phone Screening</MenuItem>
              <MenuItem value="MANAGER">Hiring Manager</MenuItem>
            </Select>
          </FormControl>
        </Grid>

        {/* Job URL */}
        <Grid item xs={12} md={6}>
          <TextField
            fullWidth
            label="Job Posting URL (Optional)"
            placeholder="LinkedIn / Company careers link"
            value={interview.jobUrl}
            onChange={(e) => setInterview({ ...interview, jobUrl: e.target.value })}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Link color="action" />
                </InputAdornment>
              ),
            }}
          />
        </Grid>

        {/* Job Description */}
        <Grid item xs={12}>
          <TextField
            fullWidth
            label="Job Description / Requirements"
            multiline
            rows={4}
            placeholder="Paste the job requirements here for better AI analysis..."
            value={interview.jobDescription}
            onChange={(e) => setInterview({ ...interview, jobDescription: e.target.value })}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start" sx={{ alignSelf: 'flex-start', mt: 1.5 }}>
                  <Description color="action" />
                </InputAdornment>
              ),
            }}
          />
        </Grid>

       {/* Interview Date */}
      <Grid item xs={12} md={6}>
        <TextField
          fullWidth
          label="Interview Date & Time"
          type="datetime-local"
          value={interview.interviewDate}
          onChange={(e) => setInterview({ ...interview, interviewDate: e.target.value })}
          InputLabelProps={{
            shrink: true, 
          }}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <Event color="action" />
              </InputAdornment>
            ),
          }}
        />
      </Grid>


        <Grid item xs={12}>
          <Divider sx={{ my: 1 }} />
          <Button 
            type="submit" 
            variant="contained" 
            fullWidth 
            size="large"
            disabled={!interview.companyName || !interview.jobTitle}
            sx={{ 
                py: 1.5, 
                fontWeight: 'bold',
                fontSize: '1rem',
                textTransform: 'none',
                boxShadow: 3
            }}
          >
            Generate AI Preparation Kit
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};

export default InterviewForm;