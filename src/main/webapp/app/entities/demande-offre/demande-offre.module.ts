import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DemandeOffreComponent } from './list/demande-offre.component';
import { DemandeOffreDetailComponent } from './detail/demande-offre-detail.component';
import { DemandeOffreUpdateComponent } from './update/demande-offre-update.component';
import { DemandeOffreDeleteDialogComponent } from './delete/demande-offre-delete-dialog.component';
import { DemandeOffreRoutingModule } from './route/demande-offre-routing.module';

@NgModule({
  imports: [SharedModule, DemandeOffreRoutingModule],
  declarations: [DemandeOffreComponent, DemandeOffreDetailComponent, DemandeOffreUpdateComponent, DemandeOffreDeleteDialogComponent],
  entryComponents: [DemandeOffreDeleteDialogComponent],
})
export class DemandeOffreModule {}
