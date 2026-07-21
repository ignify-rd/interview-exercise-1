import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { DepartmentService } from './department.service';
import { Department } from '../../shared/models/department.model';

describe('DepartmentService', () => {
  let service: DepartmentService;
  let httpMock: HttpTestingController;

  const department: Department = {
    id: 1,
    name: 'Engineering',
    description: 'Software engineering and platform team',
    employeeCount: 10,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DepartmentService],
    });
    service = TestBed.inject(DepartmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll() should GET /api/departments', () => {
    service.getAll().subscribe((result) => {
      expect(result).toEqual([department]);
    });

    const req = httpMock.expectOne('/api/departments');
    expect(req.request.method).toBe('GET');
    req.flush([department]);
  });

  it('getAll() should propagate errors', () => {
    let capturedStatus = 0;
    service.getAll().subscribe({
      next: () => fail('expected an error, not departments'),
      error: (err) => (capturedStatus = err.status),
    });

    const req = httpMock.expectOne('/api/departments');
    req.flush('Server error', { status: 500, statusText: 'Server Error' });
    expect(capturedStatus).toBe(500);
  });
});
