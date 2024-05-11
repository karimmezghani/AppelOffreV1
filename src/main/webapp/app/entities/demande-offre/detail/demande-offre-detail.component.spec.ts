import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeOffreDetailComponent } from './demande-offre-detail.component';

describe('Component Tests', () => {
  describe('DemandeOffre Management Detail Component', () => {
    let comp: DemandeOffreDetailComponent;
    let fixture: ComponentFixture<DemandeOffreDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandeOffreDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandeOffre: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandeOffreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandeOffreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandeOffre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandeOffre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
