export interface EmployeeWorkload {
  id: number;
  fullName: string;
  email: string;
  department: string;
  totalTasks: number;
  activeTasks: number;
  overdueTasks: number;
  estimatedHours: number;
  completionRate: number;
}
