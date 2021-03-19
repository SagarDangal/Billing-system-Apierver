package com.billingSystem.demo.repo;

import com.billingSystem.demo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query("from Token where Userid=?1")
    public Token findByUserid(Long userid);
}
