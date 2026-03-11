import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { EmployeeService } from '../../../core/services/employee.service';
import { Employee } from '../../../shared/models/employee.model';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatIconModule,
  ],
  template: `
    <div class="page-container">
      <h2 class="page-title">Employees</h2>

      <div *ngIf="loading" class="loading-spinner">
        <mat-spinner diameter="48"></mat-spinner>
      </div>

      <mat-card *ngIf="!loading">
        <mat-card-content>
          <table mat-table [dataSource]="employees" class="mat-elevation-z1">
            <ng-container matColumnDef="name">
              <th mat-header-cell *matHeaderCellDef>Name</th>
              <td mat-cell *matCellDef="let e">{{ e.firstName }} {{ e.lastName }}</td>
            </ng-container>

            <ng-container matColumnDef="email">
              <th mat-header-cell *matHeaderCellDef>Email</th>
              <td mat-cell *matCellDef="let e">{{ e.email }}</td>
            </ng-container>

            <ng-container matColumnDef="department">
              <th mat-header-cell *matHeaderCellDef>Department</th>
              <td mat-cell *matCellDef="let e">
                <mat-chip>{{ e.departmentName }}</mat-chip>
              </td>
            </ng-container>

            <ng-container matColumnDef="hireDate">
              <th mat-header-cell *matHeaderCellDef>Hire Date</th>
              <td mat-cell *matCellDef="let e">{{ e.hireDate | date:'mediumDate' }}</td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>

          <p *ngIf="employees.length === 0" style="padding: 16px; color: grey;">
            No employees found.
          </p>
        </mat-card-content>
      </mat-card>
    </div>
  `,
})
export class EmployeeListComponent implements OnInit {
  displayedColumns = ['name', 'email', 'department', 'hireDate'];
  employees: Employee[] = [];
  loading = true;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
