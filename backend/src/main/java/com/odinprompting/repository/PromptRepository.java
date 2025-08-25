package com.odinprompting.repository;

import com.odinprompting.model.Prompt;
import com.odinprompting.model.User;
import com.odinprompting.model.AiModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    
    List<Prompt> findByUser(User user);
    
    List<Prompt> findByAiModel(AiModel aiModel);
    
    List<Prompt> findByCategory(Prompt.Category category);
    
    List<Prompt> findByIsPublicTrue();
    
    List<Prompt> findByIsTemplateTrue();
    
    Page<Prompt> findByIsPublicTrue(Pageable pageable);
    
    Page<Prompt> findByUserAndIsPublicTrue(User user, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p WHERE p.isPublic = true AND p.category = :category")
    Page<Prompt> findPublicPromptsByCategory(@Param("category") Prompt.Category category, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p WHERE p.isPublic = true AND p.aiModel = :aiModel")
    Page<Prompt> findPublicPromptsByAiModel(@Param("aiModel") AiModel aiModel, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p WHERE p.isPublic = true AND p.qualityScore >= :minScore ORDER BY p.qualityScore DESC")
    Page<Prompt> findHighQualityPrompts(@Param("minScore") Double minScore, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p WHERE p.isPublic = true AND SIZE(p.tags) > 0 AND :tag MEMBER OF p.tags")
    List<Prompt> findByTag(@Param("tag") String tag);
    
    @Query("SELECT p FROM Prompt p WHERE p.title LIKE %:searchTerm% OR p.description LIKE %:searchTerm%")
    Page<Prompt> searchPrompts(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    @Query("SELECT AVG(p.qualityScore) FROM Prompt p WHERE p.qualityScore IS NOT NULL")
    Double getAverageQualityScore();
    
    @Query("SELECT COUNT(p) FROM Prompt p WHERE p.user = :user")
    long countByUser(@Param("user") User user);
}
