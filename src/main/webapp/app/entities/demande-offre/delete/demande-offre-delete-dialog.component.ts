import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeOffre } from '../demande-offre.model';
import { DemandeOffreService } from '../service/demande-offre.service';

@Component({
  templateUrl: './demande-offre-delete-dialog.component.html',
})
export class DemandeOffreDeleteDialogComponent {
  demandeOffre?: IDemandeOffre;

  constructor(protected demandeOffreService: DemandeOffreService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeOffreService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
