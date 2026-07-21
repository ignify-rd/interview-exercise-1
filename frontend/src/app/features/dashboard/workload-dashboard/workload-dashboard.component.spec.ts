import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WorkloadDashboardComponent } from './workload-dashboard.component';

describe('WorkloadDashboardComponent', () => {
  let fixture: ComponentFixture<WorkloadDashboardComponent>;
  let component: WorkloadDashboardComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkloadDashboardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(WorkloadDashboardComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render the dashboard title and placeholder', () => {
    fixture.detectChanges();

    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(text).toContain('Employee Workload Dashboard');
    expect(text).toContain('To be implemented');
  });
});
