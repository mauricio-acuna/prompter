package com.odinprompting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;
    
    @Column(name = "expected_outcome", columnDefinition = "TEXT")
    private String expectedOutcome;
    
    @Column(name = "sample_prompt", columnDefinition = "TEXT")
    private String samplePrompt;
    
    @NotNull(message = "AI model is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_model_id", nullable = false)
    private AiModel aiModel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel = DifficultyLevel.BEGINNER;
    
    @Column(name = "estimated_time_minutes")
    private Integer estimatedTimeMinutes;
    
    @Column(name = "points_reward")
    private Integer pointsReward = 10;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "order_index")
    private Integer orderIndex = 0;
    
    @ElementCollection
    @CollectionTable(name = "exercise_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "exercise_evaluation_criteria", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "criterion", columnDefinition = "TEXT")
    private List<String> evaluationCriteria = new ArrayList<>();
    
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserProgress> userProgress = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Category {
        PROMPT_BASICS,
        ADVANCED_TECHNIQUES,
        MODEL_SPECIFIC,
        CHAIN_OF_THOUGHT,
        FEW_SHOT_LEARNING,
        ROLE_PLAYING,
        CODE_GENERATION,
        DATA_ANALYSIS,
        CREATIVE_WRITING,
        DEBUGGING
    }
    
    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    }
}
