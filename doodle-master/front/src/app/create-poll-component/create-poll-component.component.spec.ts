import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CreatePollComponentComponent } from './create-poll-component.component';
import { of } from 'rxjs';
import { PollService } from '../poll-service.service';

const pollServiceMock = {
  getPollBySlugAdminId: jasmine.createSpy().and.returnValue(
    of({ slug: 'test', pollChoices: [] })
  ),
  getComentsBySlugId: jasmine.createSpy().and.returnValue(of([])),
  createPoll: jasmine.createSpy().and.returnValue(of({})),
  selectEvent: jasmine.createSpy().and.returnValue(of({}))
};

describe('CreatePollComponentComponent', () => {
  let component: CreatePollComponentComponent;
  let fixture: ComponentFixture<CreatePollComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CreatePollComponentComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({}),
            queryParams: of({}),
            snapshot: {
              paramMap: { get: () => null },
              queryParamMap: { get: () => null }
            }
          }
        },
        { provide: PollService, useValue: pollServiceMock }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePollComponentComponent);
    component = fixture.componentInstance;

    // safe detectChanges (évite crash si ngOnInit dépend de données)
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});