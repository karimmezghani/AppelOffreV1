import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAppelOffre, AppelOffre } from '../appel-offre.model';
import { AppelOffreService } from '../service/appel-offre.service';

@Component({
  selector: 'jhi-appel-offre-update',
  templateUrl: './appel-offre-update.component.html',
})
export class AppelOffreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required]],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    exercice: [null, [Validators.required]],
    designation: [null, [Validators.required]],
  });

  constructor(protected appelOffreService: AppelOffreService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appelOffre }) => {
      if (appelOffre.id === undefined) {
        const today = dayjs().startOf('day');
        appelOffre.dateDebut = today;
        appelOffre.dateFin = today;
      }

      this.updateForm(appelOffre);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appelOffre = this.createFromForm();
    if (appelOffre.id !== undefined) {
      this.subscribeToSaveResponse(this.appelOffreService.update(appelOffre));
    } else {
      this.subscribeToSaveResponse(this.appelOffreService.create(appelOffre));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppelOffre>>): void {
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

  protected updateForm(appelOffre: IAppelOffre): void {
    this.editForm.patchValue({
      id: appelOffre.id,
      numero: appelOffre.numero,
      dateDebut: appelOffre.dateDebut ? appelOffre.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: appelOffre.dateFin ? appelOffre.dateFin.format(DATE_TIME_FORMAT) : null,
      exercice: appelOffre.exercice,
      designation: appelOffre.designation,
    });
  }

  protected createFromForm(): IAppelOffre {
    return {
      ...new AppelOffre(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? dayjs(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? dayjs(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      exercice: this.editForm.get(['exercice'])!.value,
      designation: this.editForm.get(['designation'])!.value,
    };
  }
}
