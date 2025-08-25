import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface Prompt {
  id: number;
  title: string;
  content: string;
  description?: string;
  aiModelName: string;
  aiModelProvider: string;
  userName: string;
  category: string;
  difficultyLevel: string;
  isPublic: boolean;
  isTemplate: boolean;
  qualityScore?: number;
  usageCount: number;
  ratingAverage?: number;
  ratingCount: number;
  tags: string[];
  createdAt: string;
  updatedAt: string;
}

export interface CreatePromptRequest {
  title: string;
  content: string;
  description?: string;
  aiModelId: number;
  category: string;
  difficultyLevel: string;
  isPublic: boolean;
  isTemplate: boolean;
  tags?: string[];
}

export interface PromptEvaluation {
  id: number;
  clarityScore?: number;
  specificityScore?: number;
  contextScore?: number;
  structureScore?: number;
  overallScore?: number;
  tokenCount?: number;
  estimatedCost?: number;
  feedback?: string;
  suggestions?: string;
  evaluationType: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class PromptService {
  private readonly API_URL = 'http://localhost:8080/api/prompts';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getHeaders(): HttpHeaders {
    return this.authService.getAuthHeaders();
  }

  getAllPrompts(page: number = 0, size: number = 10): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.API_URL}?page=${page}&size=${size}`, { headers });
  }

  getMyPrompts(): Observable<Prompt[]> {
    const headers = this.getHeaders();
    return this.http.get<Prompt[]>(`${this.API_URL}/my`, { headers });
  }

  getPublicPrompts(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.API_URL}/public?page=${page}&size=${size}`);
  }

  getPromptsByCategory(category: string, page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.API_URL}/category/${category}?page=${page}&size=${size}`);
  }

  getPromptsByModel(modelId: number, page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.API_URL}/model/${modelId}?page=${page}&size=${size}`);
  }

  getTemplates(): Observable<Prompt[]> {
    return this.http.get<Prompt[]>(`${this.API_URL}/templates`);
  }

  getPromptById(id: number): Observable<Prompt> {
    return this.http.get<Prompt>(`${this.API_URL}/${id}`);
  }

  createPrompt(prompt: CreatePromptRequest): Observable<Prompt> {
    const headers = this.getHeaders();
    return this.http.post<Prompt>(`${this.API_URL}`, prompt, { headers });
  }

  updatePrompt(id: number, prompt: CreatePromptRequest): Observable<Prompt> {
    const headers = this.getHeaders();
    return this.http.put<Prompt>(`${this.API_URL}/${id}`, prompt, { headers });
  }

  deletePrompt(id: number): Observable<void> {
    const headers = this.getHeaders();
    return this.http.delete<void>(`${this.API_URL}/${id}`, { headers });
  }

  searchPrompts(searchTerm: string, page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.API_URL}/search?q=${encodeURIComponent(searchTerm)}&page=${page}&size=${size}`);
  }

  evaluatePrompt(promptId: number): Observable<PromptEvaluation> {
    const headers = this.getHeaders();
    return this.http.post<PromptEvaluation>(`${this.API_URL}/${promptId}/evaluate`, {}, { headers });
  }

  getPromptEvaluations(promptId: number): Observable<PromptEvaluation[]> {
    return this.http.get<PromptEvaluation[]>(`${this.API_URL}/${promptId}/evaluations`);
  }
}
