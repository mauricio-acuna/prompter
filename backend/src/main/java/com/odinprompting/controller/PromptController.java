package com.odinprompting.controller;

import com.odinprompting.dto.PromptCreateDTO;
import com.odinprompting.dto.PromptDTO;
import com.odinprompting.model.AiModel;
import com.odinprompting.model.Prompt;
import com.odinprompting.model.User;
import com.odinprompting.repository.AiModelRepository;
import com.odinprompting.repository.PromptRepository;
import com.odinprompting.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prompts")
@CrossOrigin(origins = "http://localhost:4200")
public class PromptController {
    private final PromptRepository promptRepository;
    private final UserRepository userRepository;
    private final AiModelRepository aiModelRepository;

    public PromptController(PromptRepository promptRepository, UserRepository userRepository, AiModelRepository aiModelRepository) {
        this.promptRepository = promptRepository;
        this.userRepository = userRepository;
        this.aiModelRepository = aiModelRepository;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrompts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Prompt> promptPage = promptRepository.findByIsPublicTrue(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", promptPage.getContent().stream().map(this::convertToDTO).toList());
        response.put("totalElements", promptPage.getTotalElements());
        response.put("totalPages", promptPage.getTotalPages());
        response.put("currentPage", page);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<PromptDTO>> getMyPrompts(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Prompt> prompts = promptRepository.findByUser(user);
        List<PromptDTO> promptDTOs = prompts.stream().map(this::convertToDTO).toList();
        
        return ResponseEntity.ok(promptDTOs);
    }
    
    @GetMapping("/public")
    public ResponseEntity<Map<String, Object>> getPublicPrompts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Prompt> promptPage = promptRepository.findByIsPublicTrue(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", promptPage.getContent().stream().map(this::convertToDTO).toList());
        response.put("totalElements", promptPage.getTotalElements());
        response.put("totalPages", promptPage.getTotalPages());
        response.put("currentPage", page);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/templates")
    public ResponseEntity<List<PromptDTO>> getTemplates() {
        List<Prompt> templates = promptRepository.findByIsTemplateTrue();
        List<PromptDTO> templateDTOs = templates.stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(templateDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PromptDTO> getPromptById(@PathVariable Long id) {
        return promptRepository.findById(id)
                .map(prompt -> ResponseEntity.ok(convertToDTO(prompt)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PromptDTO> createPrompt(@Valid @RequestBody PromptCreateDTO promptCreateDTO, 
                                                 Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        AiModel aiModel = aiModelRepository.findById(promptCreateDTO.getAiModelId())
                .orElseThrow(() -> new RuntimeException("AI Model not found"));
        
        Prompt prompt = new Prompt();
        prompt.setTitle(promptCreateDTO.getTitle());
        prompt.setContent(promptCreateDTO.getContent());
        prompt.setDescription(promptCreateDTO.getDescription());
        prompt.setAiModel(aiModel);
        prompt.setUser(user);
        prompt.setCategory(promptCreateDTO.getCategory());
        prompt.setDifficultyLevel(promptCreateDTO.getDifficultyLevel());
        prompt.setIsPublic(promptCreateDTO.getIsPublic());
        prompt.setIsTemplate(promptCreateDTO.getIsTemplate());
        prompt.setTags(promptCreateDTO.getTags());
        prompt.setUsageCount(0);
        prompt.setRatingCount(0);
        
        Prompt savedPrompt = promptRepository.save(prompt);
        return ResponseEntity.ok(convertToDTO(savedPrompt));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PromptDTO> updatePrompt(@PathVariable Long id, 
                                                 @Valid @RequestBody PromptCreateDTO promptCreateDTO,
                                                 Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        
        // Check if user owns the prompt
        if (!prompt.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        
        AiModel aiModel = aiModelRepository.findById(promptCreateDTO.getAiModelId())
                .orElseThrow(() -> new RuntimeException("AI Model not found"));
        
        prompt.setTitle(promptCreateDTO.getTitle());
        prompt.setContent(promptCreateDTO.getContent());
        prompt.setDescription(promptCreateDTO.getDescription());
        prompt.setAiModel(aiModel);
        prompt.setCategory(promptCreateDTO.getCategory());
        prompt.setDifficultyLevel(promptCreateDTO.getDifficultyLevel());
        prompt.setIsPublic(promptCreateDTO.getIsPublic());
        prompt.setIsTemplate(promptCreateDTO.getIsTemplate());
        prompt.setTags(promptCreateDTO.getTags());
        
        Prompt updatedPrompt = promptRepository.save(prompt);
        return ResponseEntity.ok(convertToDTO(updatedPrompt));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrompt(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        
        // Check if user owns the prompt
        if (!prompt.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        
        promptRepository.delete(prompt);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPrompts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Prompt> promptPage = promptRepository.searchPrompts(q, pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", promptPage.getContent().stream().map(this::convertToDTO).toList());
        response.put("totalElements", promptPage.getTotalElements());
        response.put("totalPages", promptPage.getTotalPages());
        response.put("currentPage", page);
        
        return ResponseEntity.ok(response);
    }
    
    private PromptDTO convertToDTO(Prompt prompt) {
        PromptDTO dto = new PromptDTO();
        dto.setId(prompt.getId());
        dto.setTitle(prompt.getTitle());
        dto.setContent(prompt.getContent());
        dto.setDescription(prompt.getDescription());
        dto.setAiModelName(prompt.getAiModel().getName());
        dto.setAiModelProvider(prompt.getAiModel().getProvider());
        dto.setUserName(prompt.getUser().getUsername());
        dto.setCategory(prompt.getCategory());
        dto.setDifficultyLevel(prompt.getDifficultyLevel());
        dto.setIsPublic(prompt.getIsPublic());
        dto.setIsTemplate(prompt.getIsTemplate());
        dto.setQualityScore(prompt.getQualityScore());
        dto.setUsageCount(prompt.getUsageCount());
        dto.setRatingAverage(prompt.getRatingAverage());
        dto.setRatingCount(prompt.getRatingCount());
        dto.setTags(prompt.getTags());
        dto.setCreatedAt(prompt.getCreatedAt());
        dto.setUpdatedAt(prompt.getUpdatedAt());
        return dto;
    }
}
