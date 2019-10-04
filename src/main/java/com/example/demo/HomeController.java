package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String getIndex(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "list";
    }
    @RequestMapping("/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return "form";
    }
    @RequestMapping("/process")
    public String submitForm(@ModelAttribute Job job){
        Date date = new Date();
        job.setPostedDate(date);
        jobRepository.save(job);
        return "redirect:/";
    }
    @PostMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model){
        String[] searchs = search.split(" ");
        Set<Job> jobs = new HashSet<>();
        if(searchs.length == 1) {
            model.addAttribute("jobs",
                    jobRepository
                            .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrderByPostedDate
                                    (search, search, search, search));
        } else {
            for (String word : searchs){
               ArrayList<Job> jobHolder = jobRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrderByPostedDate(word,word,word,word);
               for (Job x : jobHolder){
                   jobs.add(x);
               }
            }
            model.addAttribute("jobs",jobs);
        }
        return "searchlist";
    }
    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long
                                    id, Model model) {
        model.addAttribute("job",jobRepository.findById(id).get());
        return "form";
    }
    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long
            id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job",jobRepository.findById(id).get());
        model.addAttribute("id",id);
        return "show";
    }

}
