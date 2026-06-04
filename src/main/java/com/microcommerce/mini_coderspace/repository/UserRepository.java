package com.microcommerce.mini_coderspace.repository;

import com.microcommerce.mini_coderspace.entity.JobPosting;
import com.microcommerce.mini_coderspace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
