jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AppelOffreService } from '../service/appel-offre.service';
import { IAppelOffre, AppelOffre } from '../appel-offre.model';

import { AppelOffreUpdateComponent } from './appel-offre-update.component';

describe('Component Tests', () => {
  describe('AppelOffre Management Update Component', () => {
    let comp: AppelOffreUpdateComponent;
    let fixture: ComponentFixture<AppelOffreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let appelOffreService: AppelOffreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AppelOffreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AppelOffreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppelOffreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      appelOffreService = TestBed.inject(AppelOffreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const appelOffre: IAppelOffre = { id: 456 };

        activatedRoute.data = of({ appelOffre });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(appelOffre));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const appelOffre = { id: 123 };
        spyOn(appelOffreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ appelOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: appelOffre }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(appelOffreService.update).toHaveBeenCalledWith(appelOffre);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const appelOffre = new AppelOffre();
        spyOn(appelOffreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ appelOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: appelOffre }));
        saveSubject.complete();

        // THEN
        expect(appelOffreService.create).toHaveBeenCalledWith(appelOffre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const appelOffre = { id: 123 };
        spyOn(appelOffreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ appelOffre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(appelOffreService.update).toHaveBeenCalledWith(appelOffre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
