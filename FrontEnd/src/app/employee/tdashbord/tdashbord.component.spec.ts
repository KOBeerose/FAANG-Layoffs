import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TdashbordComponent } from './tdashbord.component';

describe('TdashbordComponent', () => {
  let component: TdashbordComponent;
  let fixture: ComponentFixture<TdashbordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TdashbordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TdashbordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
