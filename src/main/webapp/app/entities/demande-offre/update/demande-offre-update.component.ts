import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandeOffre, DemandeOffre } from '../demande-offre.model';
import { DemandeOffreService } from '../service/demande-offre.service';
import { IAppelOffre } from 'app/entities/appel-offre/appel-offre.model';
import { AppelOffreService } from 'app/entities/appel-offre/service/appel-offre.service';

@Component({
  selector: 'jhi-demande-offre-update',
  templateUrl: './demande-offre-update.component.html',
})
export class DemandeOffreUpdateComponent implements OnInit {
  isSaving = false;

  appelOffresSharedCollection: IAppelOffre[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    quantite: [null, [Validators.required]],
    appelOffre: [],
  });

  constructor(
    protected demandeOffreService: DemandeOffreService,
    protected appelOffreService: AppelOffreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeOffre }) => {
      this.updateForm(demandeOffre);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeOffre = this.createFromForm();
    if (demandeOffre.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeOffreService.update(demandeOffre));
    } else {
      this.subscribeToSaveResponse(this.demandeOffreService.create(demandeOffre));
    }
  }

  trackAppelOffreById(index: number, item: IAppelOffre): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeOffre>>): void {
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

  protected updateForm(demandeOffre: IDemandeOffre): void {
    this.editForm.patchValue({
      id: demandeOffre.id,
      description: demandeOffre.description,
      quantite: demandeOffre.quantite,
      appelOffre: demandeOffre.appelOffre,
    });

    this.appelOffresSharedCollection = this.appelOffreService.addAppelOffreToCollectionIfMissing(
      this.appelOffresSharedCollection,
      demandeOffre.appelOffre
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appelOffreService
      .query()
      .pipe(map((res: HttpResponse<IAppelOffre[]>) => res.body ?? []))
      .pipe(
        map((appelOffres: IAppelOffre[]) =>
          this.appelOffreService.addAppelOffreToCollectionIfMissing(appelOffres, this.editForm.get('appelOffre')!.value)
        )
      )
      .subscribe((appelOffres: IAppelOffre[]) => (this.appelOffresSharedCollection = appelOffres));
  }

  protected createFromForm(): IDemandeOffre {
    return {
      ...new DemandeOffre(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      appelOffre: this.editForm.get(['appelOffre'])!.value,
    };
  }
}
