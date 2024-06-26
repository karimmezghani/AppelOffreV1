jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ArticleService } from '../service/article.service';
import { IArticle, Article } from '../article.model';
import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';
import { DemandeOffreService } from 'app/entities/demande-offre/service/demande-offre.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';

import { ArticleUpdateComponent } from './article-update.component';

describe('Component Tests', () => {
  describe('Article Management Update Component', () => {
    let comp: ArticleUpdateComponent;
    let fixture: ComponentFixture<ArticleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let articleService: ArticleService;
    let demandeOffreService: DemandeOffreService;
    let categorieService: CategorieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ArticleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ArticleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArticleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      articleService = TestBed.inject(ArticleService);
      demandeOffreService = TestBed.inject(DemandeOffreService);
      categorieService = TestBed.inject(CategorieService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DemandeOffre query and add missing value', () => {
        const article: IArticle = { id: 456 };
        const demandeOffre: IDemandeOffre = { id: 30661 };
        article.demandeOffre = demandeOffre;

        const demandeOffreCollection: IDemandeOffre[] = [{ id: 846 }];
        spyOn(demandeOffreService, 'query').and.returnValue(of(new HttpResponse({ body: demandeOffreCollection })));
        const additionalDemandeOffres = [demandeOffre];
        const expectedCollection: IDemandeOffre[] = [...additionalDemandeOffres, ...demandeOffreCollection];
        spyOn(demandeOffreService, 'addDemandeOffreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ article });
        comp.ngOnInit();

        expect(demandeOffreService.query).toHaveBeenCalled();
        expect(demandeOffreService.addDemandeOffreToCollectionIfMissing).toHaveBeenCalledWith(
          demandeOffreCollection,
          ...additionalDemandeOffres
        );
        expect(comp.demandeOffresSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Categorie query and add missing value', () => {
        const article: IArticle = { id: 456 };
        const categorie: ICategorie = { id: 28481 };
        article.categorie = categorie;

        const categorieCollection: ICategorie[] = [{ id: 21186 }];
        spyOn(categorieService, 'query').and.returnValue(of(new HttpResponse({ body: categorieCollection })));
        const additionalCategories = [categorie];
        const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
        spyOn(categorieService, 'addCategorieToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ article });
        comp.ngOnInit();

        expect(categorieService.query).toHaveBeenCalled();
        expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(categorieCollection, ...additionalCategories);
        expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const article: IArticle = { id: 456 };
        const demandeOffre: IDemandeOffre = { id: 21447 };
        article.demandeOffre = demandeOffre;
        const categorie: ICategorie = { id: 39301 };
        article.categorie = categorie;

        activatedRoute.data = of({ article });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(article));
        expect(comp.demandeOffresSharedCollection).toContain(demandeOffre);
        expect(comp.categoriesSharedCollection).toContain(categorie);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const article = { id: 123 };
        spyOn(articleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ article });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: article }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(articleService.update).toHaveBeenCalledWith(article);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const article = new Article();
        spyOn(articleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ article });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: article }));
        saveSubject.complete();

        // THEN
        expect(articleService.create).toHaveBeenCalledWith(article);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const article = { id: 123 };
        spyOn(articleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ article });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(articleService.update).toHaveBeenCalledWith(article);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDemandeOffreById', () => {
        it('Should return tracked DemandeOffre primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDemandeOffreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCategorieById', () => {
        it('Should return tracked Categorie primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
