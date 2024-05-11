import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IArticle, Article } from '../article.model';
import { ArticleService } from '../service/article.service';
import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';
import { DemandeOffreService } from 'app/entities/demande-offre/service/demande-offre.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';

@Component({
  selector: 'jhi-article-update',
  templateUrl: './article-update.component.html',
})
export class ArticleUpdateComponent implements OnInit {
  isSaving = false;

  demandeOffresSharedCollection: IDemandeOffre[] = [];
  categoriesSharedCollection: ICategorie[] = [];

  editForm = this.fb.group({
    id: [],
    designation: [null, [Validators.required]],
    actif: [],
    codeSaisi: [null, [Validators.required]],
    demandeOffre: [],
    categorie: [],
  });

  constructor(
    protected articleService: ArticleService,
    protected demandeOffreService: DemandeOffreService,
    protected categorieService: CategorieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ article }) => {
      this.updateForm(article);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const article = this.createFromForm();
    if (article.id !== undefined) {
      this.subscribeToSaveResponse(this.articleService.update(article));
    } else {
      this.subscribeToSaveResponse(this.articleService.create(article));
    }
  }

  trackDemandeOffreById(index: number, item: IDemandeOffre): number {
    return item.id!;
  }

  trackCategorieById(index: number, item: ICategorie): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(article: IArticle): void {
    this.editForm.patchValue({
      id: article.id,
      designation: article.designation,
      actif: article.actif,
      codeSaisi: article.codeSaisi,
      demandeOffre: article.demandeOffre,
      categorie: article.categorie,
    });

    this.demandeOffresSharedCollection = this.demandeOffreService.addDemandeOffreToCollectionIfMissing(
      this.demandeOffresSharedCollection,
      article.demandeOffre
    );
    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing(
      this.categoriesSharedCollection,
      article.categorie
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeOffreService
      .query()
      .pipe(map((res: HttpResponse<IDemandeOffre[]>) => res.body ?? []))
      .pipe(
        map((demandeOffres: IDemandeOffre[]) =>
          this.demandeOffreService.addDemandeOffreToCollectionIfMissing(demandeOffres, this.editForm.get('demandeOffre')!.value)
        )
      )
      .subscribe((demandeOffres: IDemandeOffre[]) => (this.demandeOffresSharedCollection = demandeOffres));

    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing(categories, this.editForm.get('categorie')!.value)
        )
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));
  }

  protected createFromForm(): IArticle {
    return {
      ...new Article(),
      id: this.editForm.get(['id'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      actif: this.editForm.get(['actif'])!.value,
      codeSaisi: this.editForm.get(['codeSaisi'])!.value,
      demandeOffre: this.editForm.get(['demandeOffre'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
    };
  }
}
