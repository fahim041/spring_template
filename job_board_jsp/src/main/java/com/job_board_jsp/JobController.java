package com.job_board_jsp;

import com.job_board_jsp.model.JobPost;
import com.job_board_jsp.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JobController{

    private JobService service;

    public JobController(JobService jobService){
        this.service = jobService;
    }

    @GetMapping({"/", "/home"})
    public String home(){
        return "home";
    }

    @GetMapping("/addjob")
    public String addJob(){
        return "addjob";
    }

    @PostMapping("/handleform")
    public String handleForm(JobPost job){
        service.addJob(job);
        return "success";
    }

    @GetMapping("/viewalljobs")
    public String allJobs(Model m){
        List<JobPost> jobs = service.getAllJobs();
        m.addAttribute("jobPosts", jobs);
        return "viewalljobs";
    }
}
