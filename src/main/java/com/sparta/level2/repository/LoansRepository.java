package com.sparta.level2.repository;


import com.sparta.level2.entity.LoansBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<LoansBook, Long> {
}
