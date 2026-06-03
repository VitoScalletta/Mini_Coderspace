package com.microcommerce.mini_coderspace.repository;

import com.microcommerce.mini_coderspace.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {
}
