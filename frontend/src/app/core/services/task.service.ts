import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task, PagedResponse } from '../../shared/models/task.model';

@Injectable({ providedIn: 'root' })
export class TaskService {
  constructor(private http: HttpClient) {}

  getAllTasks(): Observable<PagedResponse<Task>> {
    return this.http.get<PagedResponse<Task>>('/api/tasks', {
      params: { page: '0', size: '10000' }
    });
  }

  getTasks(page: number, size: number, employeeId?: number): Observable<PagedResponse<Task>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (employeeId !== undefined) {
      params = params.set('employeeId', employeeId.toString());
    }
    return this.http.get<PagedResponse<Task>>('/api/tasks', { params });
  }
}
