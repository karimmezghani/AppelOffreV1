import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TvaDetailComponent } from './tva-detail.component';

describe('Component Tests', () => {
  describe('Tva Management Detail Component', () => {
    let comp: TvaDetailComponent;
    let fixture: ComponentFixture<TvaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TvaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ tva: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TvaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TvaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tva on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tva).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
