package com.microcommerce.mini_coderspace.dto.response;

import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingResponse {
    private Long id;
    private String title;
    private String description;
    private JobPostingStatus status;
    private LocalDateTime createdAt;
}
