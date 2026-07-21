import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { EmployeeListComponent } from './employee-list.component';
import { EmployeeService } from '../../../core/services/employee.service';
import { Employee } from '../../../shared/models/employee.model';

describe('EmployeeListComponent', () => {
  let fixture: ComponentFixture<EmployeeListComponent>;
  let component: EmployeeListComponent;
  let employeeServiceSpy: jasmine.SpyObj<EmployeeService>;

  const employees: Employee[] = [
    {
      id: 1,
      firstName: 'Alice',
      lastName: 'Johnson',
      email: 'alice.johnson@taskflow.io',
      hireDate: '2021-03-15',
      departmentId: 1,
      departmentName: 'Engineering',
    },
  ];

  beforeEach(async () => {
    employeeServiceSpy = jasmine.createSpyObj<EmployeeService>('EmployeeService', ['getAll']);
    employeeServiceSpy.getAll.and.returnValue(of(employees));

    await TestBed.configureTestingModule({
      imports: [EmployeeListComponent],
      providers: [
        provideNoopAnimations(),
        { provide: EmployeeService, useValue: employeeServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EmployeeListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load employees on init and clear the loading flag', () => {
    fixture.detectChanges();

    expect(employeeServiceSpy.getAll).toHaveBeenCalledTimes(1);
    expect(component.loading).toBeFalse();
    expect(component.employees).toEqual(employees);
  });

  it('should render employee rows in the table', () => {
    fixture.detectChanges();

    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(text).toContain('Alice');
    expect(text).toContain('alice.johnson@taskflow.io');
    expect(text).toContain('Engineering');
  });

  it('should render an empty-state message when there are no employees', () => {
    employeeServiceSpy.getAll.and.returnValue(of([]));

    fixture.detectChanges();

    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(text).toContain('No employees found.');
  });

  it('should clear the loading flag when the service errors', () => {
    employeeServiceSpy.getAll.and.returnValue(throwError(() => new Error('boom')));

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.employees.length).toBe(0);
  });
});
