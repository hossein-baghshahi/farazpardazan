package com.farazpardazan.cardmanagementsystem.repository;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hossein Baghshahi
 */
public interface TransferRepository extends JpaRepository<Transfer, Long>, QuerydslPredicateExecutor<Transfer> {

    List<Transfer> findAllBySourceCardAndCreatedDateBetween(String sourceCard, LocalDateTime from, LocalDateTime to);

}
