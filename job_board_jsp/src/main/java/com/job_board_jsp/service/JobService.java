package com.job_board_jsp.service;

import com.job_board_jsp.model.JobPost;
import com.job_board_jsp.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private JobRepository jobRepository;

    public JobService(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public void addJob(JobPost job){
        jobRepository.addJob(job);
    }

    public List<JobPost> getAllJobs(){
        return jobRepository.getAllJobs();
    }
}
