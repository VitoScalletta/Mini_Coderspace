package com.microcommerce.mini_coderspace.controller;

import com.microcommerce.mini_coderspace.dto.request.ApplicationRequest;
import com.microcommerce.mini_coderspace.dto.request.UpdateApplicationStatusRequest;
import com.microcommerce.mini_coderspace.dto.response.ApplicationResponse;
import com.microcommerce.mini_coderspace.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse response = applicationService.createApplication(applicationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse> updateApplication(@PathVariable Long applicationId,@RequestBody UpdateApplicationStatusRequest request) {
        ApplicationResponse response = applicationService.updateApplication(applicationId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
