import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { CardSmallComponentComponent } from './card-small-component.component';

describe('CardSmallComponentComponent', () => {
  let component: CardSmallComponentComponent;
  let fixture: ComponentFixture<CardSmallComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [CardSmallComponentComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardSmallComponentComponent);
    component = fixture.componentInstance;
    component.cards = []; // évite les erreurs si le template parcourt cards
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
