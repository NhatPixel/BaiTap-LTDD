package com.example.api_ltdd.account.repository;

import com.example.api_ltdd.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);
}