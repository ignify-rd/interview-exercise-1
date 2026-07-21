import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ReportService } from './report.service';
import { EmployeeWorkload } from '../../shared/models/workload.model';

describe('ReportService', () => {
  let service: ReportService;
  let httpMock: HttpTestingController;

  const workload: EmployeeWorkload = {
    id: 1,
    fullName: 'Alice Johnson',
    email: 'alice.johnson@taskflow.io',
    department: 'Engineering',
    totalTasks: 12,
    activeTasks: 5,
    overdueTasks: 1,
    estimatedHours: 40,
    completionRate: 0.75,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReportService],
    });
    service = TestBed.inject(ReportService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getWorkload() should GET /api/reports/workload', () => {
    service.getWorkload().subscribe((result) => {
      expect(result).toEqual([workload]);
    });

    const req = httpMock.expectOne('/api/reports/workload');
    expect(req.request.method).toBe('GET');
    req.flush([workload]);
  });

  it('getWorkload() should propagate errors', () => {
    let capturedStatus = 0;
    service.getWorkload().subscribe({
      next: () => fail('expected an error, not workload data'),
      error: (err) => (capturedStatus = err.status),
    });

    const req = httpMock.expectOne('/api/reports/workload');
    req.flush('Server error', { status: 500, statusText: 'Server Error' });
    expect(capturedStatus).toBe(500);
  });
});
