package com.microcommerce.mini_coderspace.controller;

import com.microcommerce.mini_coderspace.dto.request.CreateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.request.UpdateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import com.microcommerce.mini_coderspace.service.JobPostingService;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<JobPostingResponse> createJobPosting(@RequestBody CreateJobPostingRequest createJobPostingRequest) {
        JobPostingResponse response = jobPostingService.createJobPosting(createJobPostingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobPostingResponse>> getAllJobPostings(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false)JobPostingStatus status
            ) {
        List<JobPostingResponse> response = jobPostingService.getAllJobPostings(keyword, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PutMapping("/{id}")
    public ResponseEntity<JobPostingResponse> updateJobPosting(@PathVariable Long id, @RequestBody UpdateJobPostingRequest request) {
        JobPostingResponse response = jobPostingService.updateJobPosting(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<JobPostingResponse> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.ok().build();
    }
}
