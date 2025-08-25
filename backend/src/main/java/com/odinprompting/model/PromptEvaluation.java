package com.odinprompting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prompt_evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptEvaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Prompt is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;
    
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "clarity_score")
    private Double clarityScore;
    
    @Column(name = "specificity_score")
    private Double specificityScore;
    
    @Column(name = "context_score")
    private Double contextScore;
    
    @Column(name = "structure_score")
    private Double structureScore;
    
    @Column(name = "overall_score")
    private Double overallScore;
    
    @Column(name = "token_count")
    private Integer tokenCount;
    
    @Column(name = "estimated_cost")
    private Double estimatedCost;
    
    @Column(columnDefinition = "TEXT")
    private String feedback;
    
    @Column(columnDefinition = "TEXT")
    private String suggestions;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_type")
    private EvaluationType evaluationType = EvaluationType.AUTOMATIC;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum EvaluationType {
        AUTOMATIC, MANUAL, PEER_REVIEW
    }
}
