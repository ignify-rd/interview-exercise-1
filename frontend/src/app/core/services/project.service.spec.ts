import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ProjectService } from './project.service';
import { Project } from '../../shared/models/project.model';

describe('ProjectService', () => {
  let service: ProjectService;
  let httpMock: HttpTestingController;

  const project: Project = {
    id: 1,
    name: 'Platform Revamp',
    description: 'Re-architect the core platform for scalability',
    startDate: '2024-01-01',
    endDate: '2024-12-31',
    status: 'ACTIVE',
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProjectService],
    });
    service = TestBed.inject(ProjectService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll() should GET /api/projects', () => {
    service.getAll().subscribe((result) => {
      expect(result).toEqual([project]);
    });

    const req = httpMock.expectOne('/api/projects');
    expect(req.request.method).toBe('GET');
    req.flush([project]);
  });

  it('getById() should GET /api/projects/:id', () => {
    service.getById(1).subscribe((result) => {
      expect(result).toEqual(project);
    });

    const req = httpMock.expectOne('/api/projects/1');
    expect(req.request.method).toBe('GET');
    req.flush(project);
  });

  it('getById() should propagate errors', () => {
    let capturedStatus = 0;
    service.getById(999).subscribe({
      next: () => fail('expected an error, not a project'),
      error: (err) => (capturedStatus = err.status),
    });

    const req = httpMock.expectOne('/api/projects/999');
    req.flush('Not found', { status: 404, statusText: 'Not Found' });
    expect(capturedStatus).toBe(404);
  });
});
