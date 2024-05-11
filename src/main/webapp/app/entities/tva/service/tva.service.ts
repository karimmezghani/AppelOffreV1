import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITva, getTvaIdentifier } from '../tva.model';

export type EntityResponseType = HttpResponse<ITva>;
export type EntityArrayResponseType = HttpResponse<ITva[]>;

@Injectable({ providedIn: 'root' })
export class TvaService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/tvas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(tva: ITva): Observable<EntityResponseType> {
    return this.http.post<ITva>(this.resourceUrl, tva, { observe: 'response' });
  }

  update(tva: ITva): Observable<EntityResponseType> {
    return this.http.put<ITva>(`${this.resourceUrl}/${getTvaIdentifier(tva) as number}`, tva, { observe: 'response' });
  }

  partialUpdate(tva: ITva): Observable<EntityResponseType> {
    return this.http.patch<ITva>(`${this.resourceUrl}/${getTvaIdentifier(tva) as number}`, tva, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITva>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITva[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTvaToCollectionIfMissing(tvaCollection: ITva[], ...tvasToCheck: (ITva | null | undefined)[]): ITva[] {
    const tvas: ITva[] = tvasToCheck.filter(isPresent);
    if (tvas.length > 0) {
      const tvaCollectionIdentifiers = tvaCollection.map(tvaItem => getTvaIdentifier(tvaItem)!);
      const tvasToAdd = tvas.filter(tvaItem => {
        const tvaIdentifier = getTvaIdentifier(tvaItem);
        if (tvaIdentifier == null || tvaCollectionIdentifiers.includes(tvaIdentifier)) {
          return false;
        }
        tvaCollectionIdentifiers.push(tvaIdentifier);
        return true;
      });
      return [...tvasToAdd, ...tvaCollection];
    }
    return tvaCollection;
  }
}
