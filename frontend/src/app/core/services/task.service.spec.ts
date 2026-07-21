import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TaskService } from './task.service';
import { PagedResponse, Task } from '../../shared/models/task.model';

describe('TaskService', () => {
  let service: TaskService;
  let httpMock: HttpTestingController;

  const task: Task = {
    id: 1,
    title: 'Task #1',
    description: 'Implement and verify the deliverable',
    status: 'IN_PROGRESS',
    priority: 'HIGH',
    estimatedHours: 4,
    actualHours: 2,
    dueDate: '2024-06-01',
    createdAt: '2024-01-01T10:00:00',
    projectId: 2,
    projectName: 'Mobile App v2',
    assignedToId: 3,
    assignedToName: 'Carol Williams',
  };

  const page: PagedResponse<Task> = {
    content: [task],
    page: 0,
    size: 20,
    totalElements: 1,
    totalPages: 1,
    last: true,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TaskService],
    });
    service = TestBed.inject(TaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAllTasks() should GET /api/tasks with page=0 and size=10000', () => {
    service.getAllTasks().subscribe((result) => {
      expect(result).toEqual(page);
    });

    const req = httpMock.expectOne(
      (r) =>
        r.url === '/api/tasks' &&
        r.params.get('page') === '0' &&
        r.params.get('size') === '10000'
    );
    expect(req.request.method).toBe('GET');
    req.flush(page);
  });

  it('getTasks() should GET /api/tasks with page and size params', () => {
    service.getTasks(2, 25).subscribe((result) => {
      expect(result.totalElements).toBe(1);
    });

    const req = httpMock.expectOne(
      (r) =>
        r.url === '/api/tasks' &&
        r.params.get('page') === '2' &&
        r.params.get('size') === '25'
    );
    expect(req.request.method).toBe('GET');
    expect(req.request.params.has('employeeId')).toBeFalse();
    req.flush(page);
  });

  it('getTasks() should include employeeId when provided', () => {
    service.getTasks(0, 20, 7).subscribe();

    const req = httpMock.expectOne(
      (r) => r.url === '/api/tasks' && r.params.get('employeeId') === '7'
    );
    expect(req.request.method).toBe('GET');
    req.flush(page);
  });

  it('getTasks() should propagate errors', () => {
    let capturedStatus = 0;
    service.getTasks(0, 20).subscribe({
      next: () => fail('expected an error, not tasks'),
      error: (err) => (capturedStatus = err.status),
    });

    const req = httpMock.expectOne((r) => r.url === '/api/tasks');
    req.flush('Server error', { status: 500, statusText: 'Server Error' });
    expect(capturedStatus).toBe(500);
  });
});
