package com.microcommerce.mini_coderspace.controller;

import com.microcommerce.mini_coderspace.dto.request.ApplicationRequest;
import com.microcommerce.mini_coderspace.dto.response.ApplicationResponse;
import com.microcommerce.mini_coderspace.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse response = applicationService.createApplication(applicationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
