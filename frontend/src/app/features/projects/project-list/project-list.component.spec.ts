import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { ProjectListComponent } from './project-list.component';
import { ProjectService } from '../../../core/services/project.service';
import { Project } from '../../../shared/models/project.model';

describe('ProjectListComponent', () => {
  let fixture: ComponentFixture<ProjectListComponent>;
  let component: ProjectListComponent;
  let projectServiceSpy: jasmine.SpyObj<ProjectService>;

  const projects: Project[] = [
    {
      id: 1,
      name: 'Platform Revamp',
      description: 'Re-architect the core platform',
      startDate: '2024-01-01',
      endDate: '2024-12-31',
      status: 'ACTIVE',
    },
  ];

  beforeEach(async () => {
    projectServiceSpy = jasmine.createSpyObj<ProjectService>('ProjectService', ['getAll']);
    projectServiceSpy.getAll.and.returnValue(of(projects));

    await TestBed.configureTestingModule({
      imports: [ProjectListComponent],
      providers: [
        provideNoopAnimations(),
        { provide: ProjectService, useValue: projectServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load projects on init and clear the loading flag', () => {
    fixture.detectChanges();

    expect(projectServiceSpy.getAll).toHaveBeenCalledTimes(1);
    expect(component.loading).toBeFalse();
    expect(component.projects).toEqual(projects);
  });

  it('should render project rows in the table', () => {
    fixture.detectChanges();

    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(text).toContain('Platform Revamp');
    expect(text).toContain('ACTIVE');
  });

  it('should clear the loading flag when the service errors', () => {
    projectServiceSpy.getAll.and.returnValue(throwError(() => new Error('boom')));

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.projects.length).toBe(0);
  });
});
