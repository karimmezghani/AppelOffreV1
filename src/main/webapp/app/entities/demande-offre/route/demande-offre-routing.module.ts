import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeOffreComponent } from '../list/demande-offre.component';
import { DemandeOffreDetailComponent } from '../detail/demande-offre-detail.component';
import { DemandeOffreUpdateComponent } from '../update/demande-offre-update.component';
import { DemandeOffreRoutingResolveService } from './demande-offre-routing-resolve.service';

const demandeOffreRoute: Routes = [
  {
    path: '',
    component: DemandeOffreComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeOffreDetailComponent,
    resolve: {
      demandeOffre: DemandeOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeOffreUpdateComponent,
    resolve: {
      demandeOffre: DemandeOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeOffreUpdateComponent,
    resolve: {
      demandeOffre: DemandeOffreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeOffreRoute)],
  exports: [RouterModule],
})
export class DemandeOffreRoutingModule {}
