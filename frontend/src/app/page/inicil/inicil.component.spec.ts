import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InicilComponent } from './inicil.component';

describe('InicilComponent', () => {
  let component: InicilComponent;
  let fixture: ComponentFixture<InicilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InicilComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InicilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
