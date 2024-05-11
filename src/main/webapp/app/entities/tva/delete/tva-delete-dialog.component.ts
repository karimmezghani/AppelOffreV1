import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITva } from '../tva.model';
import { TvaService } from '../service/tva.service';

@Component({
  templateUrl: './tva-delete-dialog.component.html',
})
export class TvaDeleteDialogComponent {
  tva?: ITva;

  constructor(protected tvaService: TvaService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tvaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
