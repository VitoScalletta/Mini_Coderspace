package com.microcommerce.mini_coderspace.dto.request;

import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobPostingRequest {
    private String title;
    private Long companyId;
    private String description;
    private JobPostingStatus status;
}
