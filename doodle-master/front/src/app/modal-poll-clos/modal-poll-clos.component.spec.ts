import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ModalPollClosComponent } from './modal-poll-clos.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ModalPollClosComponent', () => {
  let component: ModalPollClosComponent;
  let fixture: ComponentFixture<ModalPollClosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [ModalPollClosComponent]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalPollClosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
