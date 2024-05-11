import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOffre, Offre } from '../offre.model';
import { OffreService } from '../service/offre.service';
import { ITva } from 'app/entities/tva/tva.model';
import { TvaService } from 'app/entities/tva/service/tva.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';
import { DemandeOffreService } from 'app/entities/demande-offre/service/demande-offre.service';

@Component({
  selector: 'jhi-offre-update',
  templateUrl: './offre-update.component.html',
})
export class OffreUpdateComponent implements OnInit {
  isSaving = false;

  tvasCollection: ITva[] = [];
  fournisseursSharedCollection: IFournisseur[] = [];
  demandeOffresSharedCollection: IDemandeOffre[] = [];

  editForm = this.fb.group({
    id: [],
    uniteMesure: [null, [Validators.required]],
    marque: [null, [Validators.required]],
    prixUnitaire: [null, [Validators.required]],
    origine: [null, [Validators.required]],
    delaiLivraison: [null, [Validators.required]],
    observation: [],
    amc: [],
    echantillon: [],
    fodec: [],
    avecCodeBarre: [],
    conditionnement: [],
    prixConditionnement: [],
    tva: [],
    fournisseur: [],
    demandeOffre: [],
  });

  constructor(
    protected offreService: OffreService,
    protected tvaService: TvaService,
    protected fournisseurService: FournisseurService,
    protected demandeOffreService: DemandeOffreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offre }) => {
      this.updateForm(offre);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offre = this.createFromForm();
    if (offre.id !== undefined) {
      this.subscribeToSaveResponse(this.offreService.update(offre));
    } else {
      this.subscribeToSaveResponse(this.offreService.create(offre));
    }
  }

  trackTvaById(index: number, item: ITva): number {
    return item.id!;
  }

  trackFournisseurById(index: number, item: IFournisseur): number {
    return item.id!;
  }

  trackDemandeOffreById(index: number, item: IDemandeOffre): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffre>>): void {
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

  protected updateForm(offre: IOffre): void {
    this.editForm.patchValue({
      id: offre.id,
      uniteMesure: offre.uniteMesure,
      marque: offre.marque,
      prixUnitaire: offre.prixUnitaire,
      origine: offre.origine,
      delaiLivraison: offre.delaiLivraison,
      observation: offre.observation,
      amc: offre.amc,
      echantillon: offre.echantillon,
      fodec: offre.fodec,
      avecCodeBarre: offre.avecCodeBarre,
      conditionnement: offre.conditionnement,
      prixConditionnement: offre.prixConditionnement,
      tva: offre.tva,
      fournisseur: offre.fournisseur,
      demandeOffre: offre.demandeOffre,
    });

    this.tvasCollection = this.tvaService.addTvaToCollectionIfMissing(this.tvasCollection, offre.tva);
    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      offre.fournisseur
    );
    this.demandeOffresSharedCollection = this.demandeOffreService.addDemandeOffreToCollectionIfMissing(
      this.demandeOffresSharedCollection,
      offre.demandeOffre
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tvaService
      .query({ filter: 'offre-is-null' })
      .pipe(map((res: HttpResponse<ITva[]>) => res.body ?? []))
      .pipe(map((tvas: ITva[]) => this.tvaService.addTvaToCollectionIfMissing(tvas, this.editForm.get('tva')!.value)))
      .subscribe((tvas: ITva[]) => (this.tvasCollection = tvas));

    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));

    this.demandeOffreService
      .query()
      .pipe(map((res: HttpResponse<IDemandeOffre[]>) => res.body ?? []))
      .pipe(
        map((demandeOffres: IDemandeOffre[]) =>
          this.demandeOffreService.addDemandeOffreToCollectionIfMissing(demandeOffres, this.editForm.get('demandeOffre')!.value)
        )
      )
      .subscribe((demandeOffres: IDemandeOffre[]) => (this.demandeOffresSharedCollection = demandeOffres));
  }

  protected createFromForm(): IOffre {
    return {
      ...new Offre(),
      id: this.editForm.get(['id'])!.value,
      uniteMesure: this.editForm.get(['uniteMesure'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      prixUnitaire: this.editForm.get(['prixUnitaire'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      delaiLivraison: this.editForm.get(['delaiLivraison'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      amc: this.editForm.get(['amc'])!.value,
      echantillon: this.editForm.get(['echantillon'])!.value,
      fodec: this.editForm.get(['fodec'])!.value,
      avecCodeBarre: this.editForm.get(['avecCodeBarre'])!.value,
      conditionnement: this.editForm.get(['conditionnement'])!.value,
      prixConditionnement: this.editForm.get(['prixConditionnement'])!.value,
      tva: this.editForm.get(['tva'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
      demandeOffre: this.editForm.get(['demandeOffre'])!.value,
    };
  }
}
