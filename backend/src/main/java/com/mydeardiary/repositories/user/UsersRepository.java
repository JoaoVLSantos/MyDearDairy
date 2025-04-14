package com.mydeardiary.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mydeardiary.model.user.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    
    public User findByEmail(String email);
    
}