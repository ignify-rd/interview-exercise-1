import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { TaskListComponent } from './task-list.component';
import { TaskService } from '../../../core/services/task.service';
import { PagedResponse, Task } from '../../../shared/models/task.model';

describe('TaskListComponent', () => {
  let fixture: ComponentFixture<TaskListComponent>;
  let component: TaskListComponent;
  let taskServiceSpy: jasmine.SpyObj<TaskService>;

  const task: Task = {
    id: 1,
    title: 'Task #1',
    description: 'desc',
    status: 'IN_PROGRESS',
    priority: 'HIGH',
    estimatedHours: 4,
    actualHours: null,
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

  beforeEach(async () => {
    taskServiceSpy = jasmine.createSpyObj<TaskService>('TaskService', ['getAllTasks']);
    taskServiceSpy.getAllTasks.and.returnValue(of(page));

    await TestBed.configureTestingModule({
      imports: [TaskListComponent],
      providers: [
        provideNoopAnimations(),
        { provide: TaskService, useValue: taskServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load tasks on init and clear the loading flag', () => {
    fixture.detectChanges();

    expect(taskServiceSpy.getAllTasks).toHaveBeenCalledTimes(1);
    expect(component.loading).toBeFalse();
    expect(component.dataSource.data).toEqual([task]);
    expect(component.totalTasks).toBe(1);
  });

  it('should render task rows in the table', () => {
    fixture.detectChanges();

    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(text).toContain('Task #1');
    expect(text).toContain('IN_PROGRESS');
  });

  it('applyFilter() should push the trimmed lowercased filter into the datasource', () => {
    fixture.detectChanges();
    component.filterText = '  Task  ';
    component.applyFilter();
    expect(component.dataSource.filter).toBe('task');
  });

  it('statusClass() should map known statuses and default to empty string', () => {
    expect(component.statusClass('TODO')).toBe('chip-todo');
    expect(component.statusClass('DONE')).toBe('chip-done');
    expect(component.statusClass('UNKNOWN')).toBe('');
  });

  it('should clear the loading flag when the service errors', () => {
    taskServiceSpy.getAllTasks.and.returnValue(throwError(() => new Error('boom')));

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.dataSource.data.length).toBe(0);
  });
});
