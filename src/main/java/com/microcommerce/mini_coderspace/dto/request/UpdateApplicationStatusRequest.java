package com.microcommerce.mini_coderspace.dto.request;

import com.microcommerce.mini_coderspace.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateApplicationStatusRequest {
    private Long companyId;
    private ApplicationStatus newStatus;
}
