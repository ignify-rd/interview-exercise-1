import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
  ],
  template: `
    <mat-toolbar color="primary">
      <mat-icon>task_alt</mat-icon>
      <span style="margin-left: 8px; margin-right: 32px;">TaskFlow</span>

      <a mat-button routerLink="/employees" routerLinkActive="active-link">
        <mat-icon>people</mat-icon> Employees
      </a>
      <a mat-button routerLink="/projects" routerLinkActive="active-link">
        <mat-icon>folder</mat-icon> Projects
      </a>
      <a mat-button routerLink="/tasks" routerLinkActive="active-link">
        <mat-icon>checklist</mat-icon> Tasks
      </a>
      <a mat-button routerLink="/dashboard" routerLinkActive="active-link">
        <mat-icon>dashboard</mat-icon> Dashboard
      </a>
    </mat-toolbar>

    <router-outlet />
  `,
  styles: [`
    .active-link {
      background: rgba(255,255,255,0.15);
    }
    mat-toolbar a {
      margin-right: 4px;
    }
  `]
})
export class AppComponent {}
