import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService, User } from '../../services/auth.service';
import { AiModelService, AiModel } from '../../services/ai-model.service';
import { PromptService, Prompt } from '../../services/prompt.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="dashboard">
      <header class="dashboard-header">
        <h1>Welcome to Odin Prompting</h1>
        <div class="user-info" *ngIf="currentUser">
          <span>Welcome, {{ currentUser.firstName || currentUser.username }}!</span>
          <button (click)="logout()" class="logout-btn">Logout</button>
        </div>
      </header>

      <div class="dashboard-content">
        <div class="stats-grid">
          <div class="stat-card">
            <h3>Available Models</h3>
            <div class="stat-value">{{ availableModels.length }}</div>
            <p>AI models to work with</p>
          </div>

          <div class="stat-card">
            <h3>My Prompts</h3>
            <div class="stat-value">{{ myPrompts.length }}</div>
            <p>Prompts you've created</p>
          </div>

          <div class="stat-card">
            <h3>Public Prompts</h3>
            <div class="stat-value">{{ publicPromptsCount }}</div>
            <p>Community prompts available</p>
          </div>

          <div class="stat-card">
            <h3>Experience Level</h3>
            <div class="stat-value">{{ currentUser?.experienceLevel || 'N/A' }}</div>
            <p>Your current skill level</p>
          </div>
        </div>

        <div class="quick-actions">
          <h2>Quick Actions</h2>
          <div class="actions-grid">
            <a [routerLink]="['/prompt-editor']" class="action-card">
              <h3>Create New Prompt</h3>
              <p>Start building your next AI prompt</p>
            </a>

            <a [routerLink]="['/models']" class="action-card">
              <h3>Explore Models</h3>
              <p>Learn about different AI models</p>
            </a>

            <a [routerLink]="['/prompts']" class="action-card">
              <h3>Browse Prompts</h3>
              <p>Discover community prompts</p>
            </a>

            <a [routerLink]="['/exercises']" class="action-card">
              <h3>Practice Exercises</h3>
              <p>Improve your prompting skills</p>
            </a>
          </div>
        </div>

        <div class="recent-activity">
          <h2>Recent Activity</h2>
          <div class="activity-list">
            <div *ngFor="let prompt of myPrompts.slice(0, 5)" class="activity-item">
              <div class="activity-info">
                <h4>{{ prompt.title }}</h4>
                <p>Created for {{ prompt.aiModelName }} • {{ prompt.category }}</p>
                <span class="activity-date">{{ formatDate(prompt.createdAt) }}</span>
              </div>
              <div class="activity-actions">
                <a [routerLink]="['/prompts', prompt.id]" class="view-btn">View</a>
              </div>
            </div>

            <div *ngIf="myPrompts.length === 0" class="no-activity">
              <p>No prompts yet. <a [routerLink]="['/prompt-editor']">Create your first prompt!</a></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard {
      min-height: 100vh;
      background: #f5f5f5;
    }

    .dashboard-header {
      background: white;
      padding: 1rem 2rem;
      border-bottom: 1px solid #e0e0e0;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .dashboard-header h1 {
      margin: 0;
      color: #333;
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .logout-btn {
      background: #f44336;
      color: white;
      border: none;
      padding: 0.5rem 1rem;
      border-radius: 4px;
      cursor: pointer;
    }

    .logout-btn:hover {
      background: #d32f2f;
    }

    .dashboard-content {
      padding: 2rem;
      max-width: 1200px;
      margin: 0 auto;
    }

    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 1rem;
      margin-bottom: 3rem;
    }

    .stat-card {
      background: white;
      padding: 1.5rem;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      text-align: center;
    }

    .stat-card h3 {
      margin: 0 0 1rem 0;
      color: #666;
      font-size: 0.9rem;
      text-transform: uppercase;
    }

    .stat-value {
      font-size: 2rem;
      font-weight: bold;
      color: #667eea;
      margin-bottom: 0.5rem;
    }

    .stat-card p {
      margin: 0;
      color: #999;
      font-size: 0.85rem;
    }

    .quick-actions {
      margin-bottom: 3rem;
    }

    .quick-actions h2 {
      margin-bottom: 1rem;
      color: #333;
    }

    .actions-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 1rem;
    }

    .action-card {
      background: white;
      padding: 1.5rem;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      text-decoration: none;
      color: inherit;
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .action-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
    }

    .action-card h3 {
      margin: 0 0 0.5rem 0;
      color: #667eea;
    }

    .action-card p {
      margin: 0;
      color: #666;
      font-size: 0.9rem;
    }

    .recent-activity h2 {
      margin-bottom: 1rem;
      color: #333;
    }

    .activity-list {
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .activity-item {
      padding: 1rem;
      border-bottom: 1px solid #f0f0f0;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .activity-item:last-child {
      border-bottom: none;
    }

    .activity-info h4 {
      margin: 0 0 0.25rem 0;
      color: #333;
    }

    .activity-info p {
      margin: 0 0 0.25rem 0;
      color: #666;
      font-size: 0.85rem;
    }

    .activity-date {
      font-size: 0.75rem;
      color: #999;
    }

    .view-btn {
      background: #667eea;
      color: white;
      padding: 0.25rem 0.75rem;
      border-radius: 4px;
      text-decoration: none;
      font-size: 0.85rem;
    }

    .view-btn:hover {
      background: #5a6fd8;
    }

    .no-activity {
      padding: 2rem;
      text-align: center;
      color: #666;
    }

    .no-activity a {
      color: #667eea;
      text-decoration: none;
    }

    .no-activity a:hover {
      text-decoration: underline;
    }
  `]
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  availableModels: AiModel[] = [];
  myPrompts: Prompt[] = [];
  publicPromptsCount = 0;

  constructor(
    private authService: AuthService,
    private aiModelService: AiModelService,
    private promptService: PromptService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
  }

  private loadDashboardData(): void {
    // Load available models
    this.aiModelService.getActiveModels().subscribe({
      next: (models) => this.availableModels = models,
      error: (error) => console.error('Error loading models:', error)
    });

    // Load user's prompts
    this.promptService.getMyPrompts().subscribe({
      next: (prompts) => this.myPrompts = prompts,
      error: (error) => console.error('Error loading my prompts:', error)
    });

    // Load public prompts count
    this.promptService.getPublicPrompts(0, 1).subscribe({
      next: (response) => this.publicPromptsCount = response.totalElements || 0,
      error: (error) => console.error('Error loading public prompts count:', error)
    });
  }

  logout(): void {
    this.authService.logout();
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString();
  }
}
