import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-workload-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page-container">
      <h2 class="page-title">Employee Workload Dashboard</h2>
      <p class="placeholder">To be implemented</p>
    </div>
  `,
  styles: [`
    .page-container { padding: 24px; }
    .page-title { margin-bottom: 24px; }
    .placeholder { color: #888; font-style: italic; }
  `],
})
export class WorkloadDashboardComponent {}
