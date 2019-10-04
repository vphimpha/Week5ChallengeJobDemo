package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface JobRepository extends CrudRepository<Job, Long> {

    ArrayList<Job> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrderByPostedDate (String title,String description, String author,String phone);


}
