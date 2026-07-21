import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../shared/models/employee.model';

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  constructor(private http: HttpClient) {}

  getAll(departmentId?: number): Observable<Employee[]> {
    const params = departmentId ? { departmentId: departmentId.toString() } : undefined;
    return this.http.get<Employee[]>('/api/employees', { params });
  }

  getById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`/api/employees/${id}`);
  }
}
