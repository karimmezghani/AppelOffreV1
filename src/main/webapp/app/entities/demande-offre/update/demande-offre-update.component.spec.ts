jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandeOffreService } from '../service/demande-offre.service';
import { IDemandeOffre, DemandeOffre } from '../demande-offre.model';
import { IAppelOffre } from 'app/entities/appel-offre/appel-offre.model';
import { AppelOffreService } from 'app/entities/appel-offre/service/appel-offre.service';

import { DemandeOffreUpdateComponent } from './demande-offre-update.component';

describe('Component Tests', () => {
  describe('DemandeOffre Management Update Component', () => {
    let comp: DemandeOffreUpdateComponent;
    let fixture: ComponentFixture<DemandeOffreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandeOffreService: DemandeOffreService;
    let appelOffreService: AppelOffreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandeOffreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandeOffreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandeOffreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandeOffreService = TestBed.inject(DemandeOffreService);
      appelOffreService = TestBed.inject(AppelOffreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call AppelOffre query and add missing value', () => {
        const demandeOffre: IDemandeOffre = { id: 456 };
        const appelOffre: IAppelOffre = { id: 95817 };
        demandeOffre.appelOffre = appelOffre;

        const appelOffreCollection: IAppelOffre[] = [{ id: 60171 }];
        spyOn(appelOffreService, 'query').and.returnValue(of(new HttpResponse({ body: appelOffreCollection })));
        const additionalAppelOffres = [appelOffre];
        const expectedCollection: IAppelOffre[] = [...additionalAppelOffres, ...appelOffreCollection];
        spyOn(appelOffreService, 'addAppelOffreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ demandeOffre });
        comp.ngOnInit();

        expect(appelOffreService.query).toHaveBeenCalled();
        expect(appelOffreService.addAppelOffreToCollectionIfMissing).toHaveBeenCalledWith(appelOffreCollection, ...additionalAppelOffres);
        expect(comp.appelOffresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demandeOffre: IDemandeOffre = { id: 456 };
        const appelOffre: IAppelOffre = { id: 82777 };
        demandeOffre.appelOffre = appelOffre;

        activatedRoute.data = of({ demandeOffre });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandeOffre));
        expect(comp.appelOffresSharedCollection).toContain(appelOffre);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const demandeOffre = { id: 123 };
        spyOn(demandeOffreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandeOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandeOffre }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandeOffreService.update).toHaveBeenCalledWith(demandeOffre);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const demandeOffre = new DemandeOffre();
        spyOn(demandeOffreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandeOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandeOffre }));
        saveSubject.complete();

        // THEN
        expect(demandeOffreService.create).toHaveBeenCalledWith(demandeOffre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const demandeOffre = { id: 123 };
        spyOn(demandeOffreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandeOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandeOffreService.update).toHaveBeenCalledWith(demandeOffre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAppelOffreById', () => {
        it('Should return tracked AppelOffre primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAppelOffreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
