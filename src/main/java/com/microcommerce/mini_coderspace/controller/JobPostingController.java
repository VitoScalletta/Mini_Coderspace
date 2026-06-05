package com.microcommerce.mini_coderspace.controller;

import com.microcommerce.mini_coderspace.dto.request.CreateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.service.JobPostingService;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingResponse> createJobPosting(@RequestBody CreateJobPostingRequest createJobPostingRequest) {
        JobPostingResponse response = jobPostingService.createJobPosting(createJobPostingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
