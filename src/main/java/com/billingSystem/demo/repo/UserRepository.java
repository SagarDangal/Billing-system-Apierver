package com.billingSystem.demo.repo;

import com.billingSystem.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String> {
    @Query("from User where email=?1")
    public List<User> findByEMAIL(String email);


    //@Query("from user where user_email=?1")
    public List<User> findAllByEmail(String email);

   public User findByEmail(String email);

   // public User findByEmail(String email);



    @Query("from User where email=?1 and password=?2")
    public User findByUsernamePassword(String username, String password);

}
