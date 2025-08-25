import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface AiModel {
  id: number;
  name: string;
  provider: string;
  version: string;
  description: string;
  maxTokens: number;
  contextWindow: number;
  supportsSystemPrompt: boolean;
  supportsFunctionCalling: boolean;
  supportsMultimodal: boolean;
  costPer1kTokens: number;
  isActive: boolean;
  apiEndpoint: string;
  capabilities: string[];
  bestPractices: string[];
  createdAt: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AiModelService {
  private readonly API_URL = 'http://localhost:8080/api/models';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getHeaders(): HttpHeaders {
    return this.authService.getAuthHeaders();
  }

  getAllModels(): Observable<AiModel[]> {
    return this.http.get<AiModel[]>(`${this.API_URL}`);
  }

  getActiveModels(): Observable<AiModel[]> {
    return this.http.get<AiModel[]>(`${this.API_URL}/active`);
  }

  getModelById(id: number): Observable<AiModel> {
    return this.http.get<AiModel>(`${this.API_URL}/${id}`);
  }

  getModelsByProvider(provider: string): Observable<AiModel[]> {
    return this.http.get<AiModel[]>(`${this.API_URL}/provider/${provider}`);
  }

  getModelsWithFunctionCalling(): Observable<AiModel[]> {
    return this.http.get<AiModel[]>(`${this.API_URL}/function-calling`);
  }

  getMultimodalModels(): Observable<AiModel[]> {
    return this.http.get<AiModel[]>(`${this.API_URL}/multimodal`);
  }
}
