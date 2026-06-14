package com.microcommerce.mini_coderspace.service;

import com.microcommerce.mini_coderspace.dto.request.ApplicationRequest;
import com.microcommerce.mini_coderspace.dto.request.UpdateApplicationStatusRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    public ApplicationResponse createApplication(ApplicationRequest request) {
        Long jobPostingId = request.getJobPostingId();

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("İlan bulunamadı ID:  " + jobPostingId));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User candidate = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı " ));

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

    public ApplicationResponse updateApplication(Long applicationId,UpdateApplicationStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new  RuntimeException("Başvuru bulunamadı ID : "+ applicationId));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User company = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Sisteme giriş yapan şirket bulunamadı!"));

        if (company.getUserType() != UserType.COMPANY || !company.getId().equals(application.getJobPosting().getCompany().getId())) {
            throw  new RuntimeException("Başvuruya Müdahale edemezsiniz!!");
        }
        application.setApplicationStatus(request.getNewStatus());
        Application savedApplication = applicationRepository.save(application);
        return new ApplicationResponse(
                savedApplication.getId(),
                savedApplication.getApplicationStatus(),
                savedApplication.getCreatedAt(),
                savedApplication.getUser().getId(),
                savedApplication.getJobPosting().getId()
        );
    }
}
