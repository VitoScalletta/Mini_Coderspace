package com.microcommerce.mini_coderspace.service;

import com.microcommerce.mini_coderspace.dto.request.ApplicationRequest;
import com.microcommerce.mini_coderspace.dto.response.ApplicationResponse;
import com.microcommerce.mini_coderspace.entity.Application;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.entity.User;
import com.microcommerce.mini_coderspace.enums.ApplicationStatus;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import com.microcommerce.mini_coderspace.enums.UserType;
import com.microcommerce.mini_coderspace.repository.ApplicationRepository;
import com.microcommerce.mini_coderspace.repository.JobPostingRepository;
import com.microcommerce.mini_coderspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    public ApplicationResponse createApplication(ApplicationRequest request) {
        Long candidateId = request.getCandidateId();
        Long jobPostingId = request.getJobPostingId();

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("İlan bulunamadı ID:  " + jobPostingId));
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı ID: " + candidateId ));

        if (candidate.getUserType() != UserType.CANDIDATE) {
            throw new RuntimeException("Sadece adaylar ilanlara başvurabilir");
        }

        if (jobPosting.getStatus() != JobPostingStatus.ACTIVE){
            throw new RuntimeException("İlan aktif değil");
        }

        boolean isAlreadyApplied = applicationRepository.existsByUserIdAndJobPostingId(candidate.getId(),jobPostingId);
        if (isAlreadyApplied) {
            throw new RuntimeException("Bu ilana zaten başvurdunuz");
        }
        Application application = new Application();
        application.setUser(candidate);
        application.setJobPosting(jobPosting);
        application.setCreatedAt(LocalDateTime.now());
        application.setApplicationStatus(ApplicationStatus.PENDING);
        Application savedApplication = applicationRepository.save(application);
        return new ApplicationResponse(
                savedApplication.getId(),
                savedApplication.getApplicationStatus(),
                savedApplication.getCreatedAt(),
                candidate.getId(),
                jobPosting.getId()
        );
    }
}
