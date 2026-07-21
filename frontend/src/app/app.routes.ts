import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'employees',
    pathMatch: 'full'
  },
  {
    path: 'employees',
    loadComponent: () =>
      import('./features/employees/employee-list/employee-list.component')
        .then(m => m.EmployeeListComponent)
  },
  {
    path: 'projects',
    loadComponent: () =>
      import('./features/projects/project-list/project-list.component')
        .then(m => m.ProjectListComponent)
  },
  {
    path: 'tasks',
    loadComponent: () =>
      import('./features/tasks/task-list/task-list.component')
        .then(m => m.TaskListComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/workload-dashboard/workload-dashboard.component')
        .then(m => m.WorkloadDashboardComponent)
  },
  {
    path: '**',
    redirectTo: 'employees'
  }
];
