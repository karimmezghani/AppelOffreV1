import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppelOffreDetailComponent } from './appel-offre-detail.component';

describe('Component Tests', () => {
  describe('AppelOffre Management Detail Component', () => {
    let comp: AppelOffreDetailComponent;
    let fixture: ComponentFixture<AppelOffreDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AppelOffreDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ appelOffre: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AppelOffreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppelOffreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appelOffre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appelOffre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
