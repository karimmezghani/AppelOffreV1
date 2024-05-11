import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TvaComponent } from '../list/tva.component';
import { TvaDetailComponent } from '../detail/tva-detail.component';
import { TvaUpdateComponent } from '../update/tva-update.component';
import { TvaRoutingResolveService } from './tva-routing-resolve.service';

const tvaRoute: Routes = [
  {
    path: '',
    component: TvaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TvaDetailComponent,
    resolve: {
      tva: TvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TvaUpdateComponent,
    resolve: {
      tva: TvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TvaUpdateComponent,
    resolve: {
      tva: TvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tvaRoute)],
  exports: [RouterModule],
})
export class TvaRoutingModule {}
