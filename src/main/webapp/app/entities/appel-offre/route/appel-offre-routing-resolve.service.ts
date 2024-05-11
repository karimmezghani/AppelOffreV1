import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAppelOffre, AppelOffre } from '../appel-offre.model';
import { AppelOffreService } from '../service/appel-offre.service';

@Injectable({ providedIn: 'root' })
export class AppelOffreRoutingResolveService implements Resolve<IAppelOffre> {
  constructor(protected service: AppelOffreService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppelOffre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((appelOffre: HttpResponse<AppelOffre>) => {
          if (appelOffre.body) {
            return of(appelOffre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AppelOffre());
  }
}
