import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppelOffre } from '../appel-offre.model';
import { AppelOffreService } from '../service/appel-offre.service';

@Component({
  templateUrl: './appel-offre-delete-dialog.component.html',
})
export class AppelOffreDeleteDialogComponent {
  appelOffre?: IAppelOffre;

  constructor(protected appelOffreService: AppelOffreService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appelOffreService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
