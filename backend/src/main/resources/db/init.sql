-- Initial data for AI Models
INSERT INTO ai_models (name, display_name, description, provider, api_endpoint, max_tokens, context_window, prompt_format, best_practices, active, created_at, updated_at) VALUES
('claude-3-sonnet', 'Claude 3 Sonnet', 'Anthropic''s Claude 3 Sonnet model - balanced performance and speed', 'ANTHROPIC', 'https://api.anthropic.com/v1/messages', 4096, 200000, 
'{"system": "string", "messages": [{"role": "user", "content": "string"}]}',
'Use clear, structured prompts. Prefer XML tags for complex instructions: <instructions>, <context>, <format>. Be specific about desired output format.', 
true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('gpt-4', 'GPT-4', 'OpenAI''s most capable model for complex tasks', 'OPENAI', 'https://api.openai.com/v1/chat/completions', 4096, 8192,
'{"model": "gpt-4", "messages": [{"role": "system", "content": "string"}, {"role": "user", "content": "string"}]}',
'Use clear role definitions in system messages. Provide examples for complex tasks. Structure prompts with markdown for better readability.',
true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('gemini-pro', 'Gemini Pro', 'Google''s advanced AI model with multimodal capabilities', 'GOOGLE', 'https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent', 8192, 32768,
'{"contents": [{"parts": [{"text": "string"}]}]}',
'Leverage technical precision and detailed explanations. Works well with structured data and code analysis. Use clear, specific instructions.',
true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Initial exercises for training
INSERT INTO exercises (title, description, instructions, target_model_id, difficulty, category, sample_prompt, expected_output, evaluation_criteria, max_score, time_limit_minutes, active, created_at, updated_at) VALUES
('Basic Prompt Structure', 'Learn the fundamentals of prompt construction', 
'Create a prompt that asks the AI to explain a complex programming concept in simple terms. Your prompt should include: 1) Clear role definition 2) Specific context 3) Desired output format', 
1, 'BEGINNER', 'BASIC_PROMPTING',
'You are an experienced programming instructor. Explain the concept of recursion to a beginner programmer. Use simple language and provide a practical example in Python. Format your response with: 1) Definition 2) Simple analogy 3) Code example 4) Common use cases',
'A clear, structured explanation with definition, analogy, code example, and use cases',
'Clarity (25 points), Structure (25 points), Completeness (25 points), Practical examples (25 points)',
100, 15, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Code Review Prompt', 'Create effective prompts for code analysis', 
'Write a prompt that will help the AI perform a comprehensive code review. Include specific areas to focus on and desired output format.',
1, 'INTERMEDIATE', 'OUTPUT_FORMATTING',
'You are a senior software engineer conducting a code review. Analyze the following code for: 1) Security vulnerabilities 2) Performance issues 3) Code quality and maintainability 4) Best practices adherence. Provide specific recommendations with code examples where applicable. Format as: ## Issues Found, ## Recommendations, ## Example Improvements',
'Structured code review with specific categories and actionable recommendations',
'Specificity (30 points), Structure (25 points), Actionability (25 points), Completeness (20 points)',
100, 20, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sample templates
INSERT INTO prompts (title, content, description, user_id, ai_model_id, type, category, quality_score, clarity_score, specificity_score, structure_score, is_public, is_template, created_at, updated_at) VALUES
('Code Review Template', 
'You are an expert software engineer performing a code review. Analyze the following code and provide feedback on:

## Code to Review:
[INSERT_CODE_HERE]

## Review Criteria:
1. **Security**: Check for vulnerabilities, input validation, authentication issues
2. **Performance**: Identify bottlenecks, inefficient algorithms, memory usage
3. **Maintainability**: Code clarity, documentation, naming conventions
4. **Best Practices**: Language-specific conventions, design patterns

## Output Format:
### Issues Found
- **[Category]**: Description with line numbers
- **[Category]**: Description with line numbers

### Recommendations
- Specific improvement suggestions with examples
- Priority level (High/Medium/Low)

### Example Improvements
```language
// Before
[problematic code]

// After
[improved code]
```

Focus on actionable feedback with clear explanations.',
'Comprehensive code review template for thorough analysis',
null, 1, 'TEMPLATE', 'CODE_REVIEW', 9.2, 9.5, 9.0, 9.1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Documentation Generator', 
'You are a technical writer specializing in API documentation. Generate comprehensive documentation for the following code:

## Code/API to Document:
[INSERT_CODE_HERE]

## Documentation Requirements:
1. **Overview**: Brief description of purpose and functionality
2. **Parameters**: List all parameters with types and descriptions
3. **Return Values**: Describe return types and possible values
4. **Examples**: Provide practical usage examples
5. **Error Handling**: Document possible errors and exceptions
6. **Best Practices**: Usage recommendations and tips

## Output Format:
# [Function/API Name]

## Overview
Brief description...

## Syntax
```language
function_signature
```

## Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| param1    | type | Yes      | description |

## Return Value
Description of return value...

## Examples
```language
// Basic usage
code_example

// Advanced usage
advanced_example
```

## Error Handling
- **ErrorType**: When it occurs and how to handle
- **ErrorType**: When it occurs and how to handle

## Best Practices
- Recommendation 1
- Recommendation 2

Ensure documentation is clear, complete, and follows standard conventions.',
'Template for generating comprehensive technical documentation',
null, 1, 'TEMPLATE', 'DOCUMENTATION', 8.8, 9.0, 8.5, 9.2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
