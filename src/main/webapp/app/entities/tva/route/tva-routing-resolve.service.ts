import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITva, Tva } from '../tva.model';
import { TvaService } from '../service/tva.service';

@Injectable({ providedIn: 'root' })
export class TvaRoutingResolveService implements Resolve<ITva> {
  constructor(protected service: TvaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITva> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tva: HttpResponse<Tva>) => {
          if (tva.body) {
            return of(tva.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tva());
  }
}
