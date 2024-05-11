import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeOffre, DemandeOffre } from '../demande-offre.model';
import { DemandeOffreService } from '../service/demande-offre.service';

@Injectable({ providedIn: 'root' })
export class DemandeOffreRoutingResolveService implements Resolve<IDemandeOffre> {
  constructor(protected service: DemandeOffreService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeOffre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeOffre: HttpResponse<DemandeOffre>) => {
          if (demandeOffre.body) {
            return of(demandeOffre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeOffre());
  }
}
