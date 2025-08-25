package com.odinprompting.repository;

import com.odinprompting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    java.util.List<User> findAllActiveUsers();
    
    @Query("SELECT u FROM User u WHERE u.experienceLevel = :level")
    java.util.List<User> findByExperienceLevel(@Param("level") User.ExperienceLevel level);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();
}
