import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AppelOffreComponent } from './list/appel-offre.component';
import { AppelOffreDetailComponent } from './detail/appel-offre-detail.component';
import { AppelOffreUpdateComponent } from './update/appel-offre-update.component';
import { AppelOffreDeleteDialogComponent } from './delete/appel-offre-delete-dialog.component';
import { AppelOffreRoutingModule } from './route/appel-offre-routing.module';

@NgModule({
  imports: [SharedModule, AppelOffreRoutingModule],
  declarations: [AppelOffreComponent, AppelOffreDetailComponent, AppelOffreUpdateComponent, AppelOffreDeleteDialogComponent],
  entryComponents: [AppelOffreDeleteDialogComponent],
})
export class AppelOffreModule {}
