import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITva, Tva } from '../tva.model';
import { TvaService } from '../service/tva.service';

@Component({
  selector: 'jhi-tva-update',
  templateUrl: './tva-update.component.html',
})
export class TvaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tauxTva: [null, [Validators.required]],
  });

  constructor(protected tvaService: TvaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tva }) => {
      this.updateForm(tva);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tva = this.createFromForm();
    if (tva.id !== undefined) {
      this.subscribeToSaveResponse(this.tvaService.update(tva));
    } else {
      this.subscribeToSaveResponse(this.tvaService.create(tva));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITva>>): void {
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

  protected updateForm(tva: ITva): void {
    this.editForm.patchValue({
      id: tva.id,
      tauxTva: tva.tauxTva,
    });
  }

  protected createFromForm(): ITva {
    return {
      ...new Tva(),
      id: this.editForm.get(['id'])!.value,
      tauxTva: this.editForm.get(['tauxTva'])!.value,
    };
  }
}
