package com.odinprompting.dto;

import com.odinprompting.model.Prompt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptCreateDTO {
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String description;
    
    @NotNull(message = "AI model ID is required")
    private Long aiModelId;
    
    @NotNull(message = "Category is required")
    private Prompt.Category category;
    
    private Prompt.DifficultyLevel difficultyLevel = Prompt.DifficultyLevel.INTERMEDIATE;
    private Boolean isPublic = false;
    private Boolean isTemplate = false;
    private List<String> tags;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getAiModelId() { return aiModelId; }
    public void setAiModelId(Long aiModelId) { this.aiModelId = aiModelId; }
    public Prompt.Category getCategory() { return category; }
    public void setCategory(Prompt.Category category) { this.category = category; }
    public Prompt.DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(Prompt.DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
    public Boolean getIsTemplate() { return isTemplate; }
    public void setIsTemplate(Boolean isTemplate) { this.isTemplate = isTemplate; }
    public java.util.List<String> getTags() { return tags; }
    public void setTags(java.util.List<String> tags) { this.tags = tags; }
}
