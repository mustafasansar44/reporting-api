package com.msansar.ReportingAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msansar.ReportingAPI.model.User;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
