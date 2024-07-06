package com.sparta.level2.repository;

import com.sparta.level2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNum(String phoneNum);
}
