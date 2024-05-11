jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OffreService } from '../service/offre.service';
import { IOffre, Offre } from '../offre.model';
import { ITva } from 'app/entities/tva/tva.model';
import { TvaService } from 'app/entities/tva/service/tva.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';
import { DemandeOffreService } from 'app/entities/demande-offre/service/demande-offre.service';

import { OffreUpdateComponent } from './offre-update.component';

describe('Component Tests', () => {
  describe('Offre Management Update Component', () => {
    let comp: OffreUpdateComponent;
    let fixture: ComponentFixture<OffreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let offreService: OffreService;
    let tvaService: TvaService;
    let fournisseurService: FournisseurService;
    let demandeOffreService: DemandeOffreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OffreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OffreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OffreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      offreService = TestBed.inject(OffreService);
      tvaService = TestBed.inject(TvaService);
      fournisseurService = TestBed.inject(FournisseurService);
      demandeOffreService = TestBed.inject(DemandeOffreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call tva query and add missing value', () => {
        const offre: IOffre = { id: 456 };
        const tva: ITva = { id: 18266 };
        offre.tva = tva;

        const tvaCollection: ITva[] = [{ id: 84078 }];
        spyOn(tvaService, 'query').and.returnValue(of(new HttpResponse({ body: tvaCollection })));
        const expectedCollection: ITva[] = [tva, ...tvaCollection];
        spyOn(tvaService, 'addTvaToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        expect(tvaService.query).toHaveBeenCalled();
        expect(tvaService.addTvaToCollectionIfMissing).toHaveBeenCalledWith(tvaCollection, tva);
        expect(comp.tvasCollection).toEqual(expectedCollection);
      });

      it('Should call Fournisseur query and add missing value', () => {
        const offre: IOffre = { id: 456 };
        const fournisseur: IFournisseur = { id: 11945 };
        offre.fournisseur = fournisseur;

        const fournisseurCollection: IFournisseur[] = [{ id: 76883 }];
        spyOn(fournisseurService, 'query').and.returnValue(of(new HttpResponse({ body: fournisseurCollection })));
        const additionalFournisseurs = [fournisseur];
        const expectedCollection: IFournisseur[] = [...additionalFournisseurs, ...fournisseurCollection];
        spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        expect(fournisseurService.query).toHaveBeenCalled();
        expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
          fournisseurCollection,
          ...additionalFournisseurs
        );
        expect(comp.fournisseursSharedCollection).toEqual(expectedCollection);
      });

      it('Should call DemandeOffre query and add missing value', () => {
        const offre: IOffre = { id: 456 };
        const demandeOffre: IDemandeOffre = { id: 211 };
        offre.demandeOffre = demandeOffre;

        const demandeOffreCollection: IDemandeOffre[] = [{ id: 18538 }];
        spyOn(demandeOffreService, 'query').and.returnValue(of(new HttpResponse({ body: demandeOffreCollection })));
        const additionalDemandeOffres = [demandeOffre];
        const expectedCollection: IDemandeOffre[] = [...additionalDemandeOffres, ...demandeOffreCollection];
        spyOn(demandeOffreService, 'addDemandeOffreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        expect(demandeOffreService.query).toHaveBeenCalled();
        expect(demandeOffreService.addDemandeOffreToCollectionIfMissing).toHaveBeenCalledWith(
          demandeOffreCollection,
          ...additionalDemandeOffres
        );
        expect(comp.demandeOffresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const offre: IOffre = { id: 456 };
        const tva: ITva = { id: 91599 };
        offre.tva = tva;
        const fournisseur: IFournisseur = { id: 19742 };
        offre.fournisseur = fournisseur;
        const demandeOffre: IDemandeOffre = { id: 25892 };
        offre.demandeOffre = demandeOffre;

        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(offre));
        expect(comp.tvasCollection).toContain(tva);
        expect(comp.fournisseursSharedCollection).toContain(fournisseur);
        expect(comp.demandeOffresSharedCollection).toContain(demandeOffre);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const offre = { id: 123 };
        spyOn(offreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: offre }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(offreService.update).toHaveBeenCalledWith(offre);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const offre = new Offre();
        spyOn(offreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: offre }));
        saveSubject.complete();

        // THEN
        expect(offreService.create).toHaveBeenCalledWith(offre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const offre = { id: 123 };
        spyOn(offreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ offre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(offreService.update).toHaveBeenCalledWith(offre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTvaById', () => {
        it('Should return tracked Tva primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTvaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackFournisseurById', () => {
        it('Should return tracked Fournisseur primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFournisseurById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDemandeOffreById', () => {
        it('Should return tracked DemandeOffre primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDemandeOffreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
