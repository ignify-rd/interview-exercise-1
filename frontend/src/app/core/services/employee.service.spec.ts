import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { EmployeeService } from './employee.service';
import { Employee } from '../../shared/models/employee.model';

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpMock: HttpTestingController;

  const employee: Employee = {
    id: 1,
    firstName: 'Alice',
    lastName: 'Johnson',
    email: 'alice.johnson@taskflow.io',
    hireDate: '2021-03-15',
    departmentId: 1,
    departmentName: 'Engineering',
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmployeeService],
    });
    service = TestBed.inject(EmployeeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll() should GET /api/employees without params', () => {
    service.getAll().subscribe((result) => {
      expect(result).toEqual([employee]);
    });

    const req = httpMock.expectOne('/api/employees');
    expect(req.request.method).toBe('GET');
    expect(req.request.params.keys().length).toBe(0);
    req.flush([employee]);
  });

  it('getAll(departmentId) should GET /api/employees with departmentId param', () => {
    service.getAll(2).subscribe((result) => {
      expect(result.length).toBe(1);
    });

    const req = httpMock.expectOne(
      (r) => r.url === '/api/employees' && r.params.get('departmentId') === '2'
    );
    expect(req.request.method).toBe('GET');
    req.flush([employee]);
  });

  it('getById() should GET /api/employees/:id', () => {
    service.getById(1).subscribe((result) => {
      expect(result).toEqual(employee);
    });

    const req = httpMock.expectOne('/api/employees/1');
    expect(req.request.method).toBe('GET');
    req.flush(employee);
  });

  it('getById() should propagate errors', () => {
    let capturedStatus = 0;
    service.getById(999).subscribe({
      next: () => fail('expected an error, not an employee'),
      error: (err) => (capturedStatus = err.status),
    });

    const req = httpMock.expectOne('/api/employees/999');
    req.flush('Not found', { status: 404, statusText: 'Not Found' });
    expect(capturedStatus).toBe(404);
  });
});
