import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponentComponent } from './home-component.component';
import { CardSmallComponentComponent } from '../card-small-component/card-small-component.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('HomeComponentComponent', () => {
  let component: HomeComponentComponent;
  let fixture: ComponentFixture<HomeComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [HomeComponentComponent, CardSmallComponentComponent]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
