package com.microcommerce.mini_coderspace.dto.response;

import com.microcommerce.mini_coderspace.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime createdDate;
    private Long candidateId;
    private Long jobPostingId;
}
