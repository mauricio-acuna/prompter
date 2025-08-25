package com.odinprompting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ai_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Model name is required")
    @Column(unique = true, nullable = false)
    private String name;
    
    @NotBlank(message = "Provider is required")
    @Column(nullable = false)
    private String provider;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Version is required")
    @Column(nullable = false)
    private String version;
    
    @Column(name = "max_tokens")
    private Integer maxTokens;
    
    @Column(name = "context_window")
    private Integer contextWindow;
    
    @Column(name = "supports_system_prompt")
    private Boolean supportsSystemPrompt = true;
    
    @Column(name = "supports_function_calling")
    private Boolean supportsFunctionCalling = false;
    
    @Column(name = "supports_multimodal")
    private Boolean supportsMultimodal = false;
    
    @Column(name = "cost_per_1k_tokens")
    private Double costPer1kTokens;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "api_endpoint")
    private String apiEndpoint;
    
    @ElementCollection
    @CollectionTable(name = "model_capabilities", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "capability")
    private List<String> capabilities = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "model_best_practices", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "practice", columnDefinition = "TEXT")
    private List<String> bestPractices = new ArrayList<>();
    
    @OneToMany(mappedBy = "aiModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prompt> prompts = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Integer getMaxTokens() { return maxTokens; }
    public void setMaxTokens(Integer maxTokens) { this.maxTokens = maxTokens; }
    public Integer getContextWindow() { return contextWindow; }
    public void setContextWindow(Integer contextWindow) { this.contextWindow = contextWindow; }
    public Boolean getSupportsSystemPrompt() { return supportsSystemPrompt; }
    public void setSupportsSystemPrompt(Boolean supportsSystemPrompt) { this.supportsSystemPrompt = supportsSystemPrompt; }
    public Boolean getSupportsFunctionCalling() { return supportsFunctionCalling; }
    public void setSupportsFunctionCalling(Boolean supportsFunctionCalling) { this.supportsFunctionCalling = supportsFunctionCalling; }
    public Boolean getSupportsMultimodal() { return supportsMultimodal; }
    public void setSupportsMultimodal(Boolean supportsMultimodal) { this.supportsMultimodal = supportsMultimodal; }
    public Double getCostPer1kTokens() { return costPer1kTokens; }
    public void setCostPer1kTokens(Double costPer1kTokens) { this.costPer1kTokens = costPer1kTokens; }
}
