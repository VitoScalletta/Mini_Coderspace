package com.microcommerce.mini_coderspace.service;

import com.microcommerce.mini_coderspace.dto.request.CreateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.request.UpdateJobPostingRequest;
import com.microcommerce.mini_coderspace.dto.response.JobPostingResponse;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.entity.User;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import com.microcommerce.mini_coderspace.repository.JobPostingRepository;
import com.microcommerce.mini_coderspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

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
        jobPosting.setCompany(company);
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        return new JobPostingResponse(
                savedJobPosting.getId(),
                savedJobPosting.getTitle(),
                savedJobPosting.getDescription(),
                savedJobPosting.getStatus(),
                savedJobPosting.getCreatedAt()
        );
    }

    public List<JobPostingResponse> getAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        return jobPostings.stream()
                .map(jobPosting -> new JobPostingResponse(
                        jobPosting.getId(),
                        jobPosting.getTitle(),
                        jobPosting.getDescription(),
                        jobPosting.getStatus(),
                        jobPosting.getCreatedAt()
                )).toList();
    }

    public JobPostingResponse updateJobPosting(Long id, UpdateJobPostingRequest updateJobPostingRequest) {
        JobPosting existJob = jobPostingRepository.findById(id).
                orElseThrow(() -> new RuntimeException("İlan bulunamadı"));
        Long companyId = updateJobPostingRequest.getCompanyId();

        if (!existJob.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Bu ilanın sahibi siz değilsiniz");
        }

        existJob.setTitle(updateJobPostingRequest.getTitle());
        existJob.setDescription(updateJobPostingRequest.getDescription());
        existJob.setStatus(updateJobPostingRequest.getStatus());

        JobPosting savedJobPosting = jobPostingRepository.save(existJob);

        return new JobPostingResponse(
                savedJobPosting.getId(),
                savedJobPosting.getTitle(),
                savedJobPosting.getDescription(),
                savedJobPosting.getStatus(),
                savedJobPosting.getCreatedAt()
        );
    }

    public void deleteJobPosting(Long id) {
        JobPosting willBeDeleted = jobPostingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İlan bulunamadı"));
        jobPostingRepository.delete(willBeDeleted);
    }
}
