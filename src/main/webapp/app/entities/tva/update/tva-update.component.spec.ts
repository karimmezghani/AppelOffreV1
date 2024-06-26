jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TvaService } from '../service/tva.service';
import { ITva, Tva } from '../tva.model';

import { TvaUpdateComponent } from './tva-update.component';

describe('Component Tests', () => {
  describe('Tva Management Update Component', () => {
    let comp: TvaUpdateComponent;
    let fixture: ComponentFixture<TvaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let tvaService: TvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TvaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TvaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TvaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      tvaService = TestBed.inject(TvaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const tva: ITva = { id: 456 };

        activatedRoute.data = of({ tva });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(tva));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const tva = { id: 123 };
        spyOn(tvaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ tva });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tva }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(tvaService.update).toHaveBeenCalledWith(tva);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const tva = new Tva();
        spyOn(tvaService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ tva });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tva }));
        saveSubject.complete();

        // THEN
        expect(tvaService.create).toHaveBeenCalledWith(tva);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const tva = { id: 123 };
        spyOn(tvaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ tva });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(tvaService.update).toHaveBeenCalledWith(tva);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
