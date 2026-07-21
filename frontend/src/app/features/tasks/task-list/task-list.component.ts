import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../../core/services/task.service';
import { Task } from '../../../shared/models/task.model';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
  ],
  template: `
    <div class="page-container">
      <h2 class="page-title">All Tasks</h2>

      <mat-form-field appearance="outline" style="width: 300px; margin-bottom: 16px;">
        <mat-label>Filter by title</mat-label>
        <input matInput [(ngModel)]="filterText" (ngModelChange)="applyFilter()" placeholder="Search...">
      </mat-form-field>

      <div *ngIf="loading" class="loading-spinner">
        <mat-spinner diameter="48"></mat-spinner>
      </div>

      <mat-card *ngIf="!loading">
        <mat-card-content>
          <p style="color: grey; font-size: 12px;">Showing {{ dataSource.filteredData.length }} of {{ totalTasks }} tasks</p>
          <table mat-table [dataSource]="dataSource" class="mat-elevation-z1">
            <ng-container matColumnDef="title">
              <th mat-header-cell *matHeaderCellDef>Title</th>
              <td mat-cell *matCellDef="let t">{{ t.title }}</td>
            </ng-container>

            <ng-container matColumnDef="status">
              <th mat-header-cell *matHeaderCellDef>Status</th>
              <td mat-cell *matCellDef="let t">
                <mat-chip [class]="statusClass(t.status)">{{ t.status }}</mat-chip>
              </td>
            </ng-container>

            <ng-container matColumnDef="priority">
              <th mat-header-cell *matHeaderCellDef>Priority</th>
              <td mat-cell *matCellDef="let t">{{ t.priority }}</td>
            </ng-container>

            <ng-container matColumnDef="assignedTo">
              <th mat-header-cell *matHeaderCellDef>Assigned To</th>
              <td mat-cell *matCellDef="let t">{{ t.assignedToName || '—' }}</td>
            </ng-container>

            <ng-container matColumnDef="project">
              <th mat-header-cell *matHeaderCellDef>Project</th>
              <td mat-cell *matCellDef="let t">{{ t.projectName || '—' }}</td>
            </ng-container>

            <ng-container matColumnDef="dueDate">
              <th mat-header-cell *matHeaderCellDef>Due Date</th>
              <td mat-cell *matCellDef="let t">{{ t.dueDate | date:'mediumDate' }}</td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>
        </mat-card-content>
      </mat-card>
    </div>
  `,
})
export class TaskListComponent implements OnInit {
  displayedColumns = ['title', 'status', 'priority', 'assignedTo', 'project', 'dueDate'];
  dataSource = new MatTableDataSource<Task>();
  totalTasks = 0;
  loading = true;
  filterText = '';

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.taskService.getAllTasks().subscribe({
      next: (response) => {
        this.dataSource.data = response.content;
        this.totalTasks = response.totalElements;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  applyFilter(): void {
    this.dataSource.filter = this.filterText.trim().toLowerCase();
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      'TODO': 'chip-todo',
      'IN_PROGRESS': 'chip-in-progress',
      'IN_REVIEW': 'chip-in-review',
      'DONE': 'chip-done',
    };
    return map[status] ?? '';
  }
}
