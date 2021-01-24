package com.farazpardazan.cardmanagementsystem.repository;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * @author Hossein Baghshahi
 */
public interface CardRepository extends JpaRepository<Card, Long>, QuerydslPredicateExecutor<Card> {

    Optional<Card> findByCardNumber(String cardNumber);

    Page<Card> findAll(Predicate predicate, Pageable pageable);
}
