package com.odinprompting.repository;

import com.odinprompting.model.AiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiModelRepository extends JpaRepository<AiModel, Long> {
    
    Optional<AiModel> findByName(String name);
    
    List<AiModel> findByProvider(String provider);
    
    List<AiModel> findByIsActiveTrue();
    
    boolean existsByName(String name);
}
