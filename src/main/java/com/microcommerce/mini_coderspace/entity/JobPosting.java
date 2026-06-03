package com.microcommerce.mini_coderspace.entity;

import com.microcommerce.mini_coderspace.enums.JobPostingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_postings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private User company;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobPostingStatus status;

    @Column(nullable = false,length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
