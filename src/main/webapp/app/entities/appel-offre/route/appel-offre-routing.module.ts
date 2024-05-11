import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AppelOffreComponent } from '../list/appel-offre.component';
import { AppelOffreDetailComponent } from '../detail/appel-offre-detail.component';
import { AppelOffreUpdateComponent } from '../update/appel-offre-update.component';
import { AppelOffreRoutingResolveService } from './appel-offre-routing-resolve.service';

const appelOffreRoute: Routes = [
  {
    path: '',
    component: AppelOffreComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppelOffreDetailComponent,
    resolve: {
      appelOffre: AppelOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppelOffreUpdateComponent,
    resolve: {
      appelOffre: AppelOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppelOffreUpdateComponent,
    resolve: {
      appelOffre: AppelOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(appelOffreRoute)],
  exports: [RouterModule],
})
export class AppelOffreRoutingModule {}
