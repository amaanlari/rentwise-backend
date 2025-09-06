package com.rentwise.repository;

import com.rentwise.model.Rent;
import com.rentwise.model.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByDueDateBetween(LocalDate start, LocalDate end);
    List<Rent> findByDueDateBeforeAndStatus(LocalDate date, RentStatus status);
}