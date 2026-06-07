package com.microcommerce.mini_coderspace.repository;

import com.microcommerce.mini_coderspace.entity.Application;
import com.microcommerce.mini_coderspace.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByUserIdAndJobPostingId(Long userId, Long jobPostingId);
}
