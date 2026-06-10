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
import org.springframework.security.core.context.SecurityContextHolder;
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

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User company = userRepository.findByEmail(userEmail)
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

    public List<JobPostingResponse> getAllJobPostings(String keyword,JobPostingStatus status) {
        List<JobPosting> jobPostings;
        if(keyword != null || status != null){
            jobPostings = jobPostingRepository.searchByKeywordAndStatus(keyword, status);
        }
        else if(keyword != null ){
            jobPostings = jobPostingRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        }
        else {
            jobPostings = jobPostingRepository.findAll();
        }

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

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!existJob.getCompany().getId().equals(userEmail)) {
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
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!willBeDeleted.getCompany().getId().equals(userEmail)) {
            throw new RuntimeException("Bu ilanı silme yetkiniz yok");
        }
        jobPostingRepository.delete(willBeDeleted);
    }
}
