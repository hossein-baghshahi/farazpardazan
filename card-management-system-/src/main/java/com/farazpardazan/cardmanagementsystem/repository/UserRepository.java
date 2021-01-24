package com.farazpardazan.cardmanagementsystem.repository;

import com.farazpardazan.cardmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * @author Hossein Baghshahi
 */
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);
}
