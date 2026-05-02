import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnswerPollComponent } from './answer-poll.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AnswerPollComponent', () => {
  let component: AnswerPollComponent;
  let fixture: ComponentFixture<AnswerPollComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnswerPollComponent],
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
    fixture = TestBed.createComponent(AnswerPollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
