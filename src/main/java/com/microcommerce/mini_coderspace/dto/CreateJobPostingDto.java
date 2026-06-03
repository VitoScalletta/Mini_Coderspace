package com.microcommerce.mini_coderspace.dto;

import com.microcommerce.mini_coderspace.entity.User;
import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobPostingDto {
    private String title;
    private Long companyId;
    private String description;
    private JobPostingStatus status;
}
