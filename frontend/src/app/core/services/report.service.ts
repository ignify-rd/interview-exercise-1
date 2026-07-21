import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeWorkload } from '../../shared/models/workload.model';

@Injectable({ providedIn: 'root' })
export class ReportService {
  constructor(private http: HttpClient) {}

  getWorkload(): Observable<EmployeeWorkload[]> {
    return this.http.get<EmployeeWorkload[]>('/api/reports/workload');
  }
}
