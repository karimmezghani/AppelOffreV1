import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TvaComponent } from './list/tva.component';
import { TvaDetailComponent } from './detail/tva-detail.component';
import { TvaUpdateComponent } from './update/tva-update.component';
import { TvaDeleteDialogComponent } from './delete/tva-delete-dialog.component';
import { TvaRoutingModule } from './route/tva-routing.module';

@NgModule({
  imports: [SharedModule, TvaRoutingModule],
  declarations: [TvaComponent, TvaDetailComponent, TvaUpdateComponent, TvaDeleteDialogComponent],
  entryComponents: [TvaDeleteDialogComponent],
})
export class TvaModule {}
