package com.microcommerce.mini_coderspace.repository;

import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title,String description);

    @Query("SELECT j FROM JobPosting j WHERE " +
            "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND j.status = :status")
    List<JobPosting> searchByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") JobPostingStatus status);
}
