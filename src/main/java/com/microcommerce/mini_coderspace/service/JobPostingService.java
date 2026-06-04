package com.microcommerce.mini_coderspace.service;

import com.microcommerce.mini_coderspace.dto.request.CreateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.entity.User;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import com.microcommerce.mini_coderspace.repository.JobPostingRepository;
import com.microcommerce.mini_coderspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    public JobPostingResponse createJobPosting(CreateJobPostingRequest createJobPostingRequest) {
        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle(createJobPostingRequest.getTitle());
        jobPosting.setDescription(createJobPostingRequest.getDescription());
        jobPosting.setCreatedAt(LocalDateTime.now());
        jobPosting.setStatus(JobPostingStatus.ACTIVE);
        User company = userRepository.findById(createJobPostingRequest.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Bu ID'ye ait şirket bulunamadı"));

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

    }
}
