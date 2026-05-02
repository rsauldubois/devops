import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminPollComponent } from './admin-poll.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AdminPollComponent', () => {
  let component: AdminPollComponent;
  let fixture: ComponentFixture<AdminPollComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminPollComponent],
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => null
              }
            }
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
