package com.sparta.level2.repository;


import com.sparta.level2.entity.LoansBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoansRepository extends JpaRepository<LoansBook, Long> {

    List<LoansBook> findByUserIdAndReturnedIsFalse(Long userId);

    List<LoansBook> findByUserId(Long userId);
}
