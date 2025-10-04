package com.msansar.ReportingAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msansar.ReportingAPI.model.Merchant;

public interface AuthRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByEmail(String email);
}
