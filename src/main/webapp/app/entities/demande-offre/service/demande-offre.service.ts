import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeOffre, getDemandeOffreIdentifier } from '../demande-offre.model';

export type EntityResponseType = HttpResponse<IDemandeOffre>;
export type EntityArrayResponseType = HttpResponse<IDemandeOffre[]>;

@Injectable({ providedIn: 'root' })
export class DemandeOffreService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-offres');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(demandeOffre: IDemandeOffre): Observable<EntityResponseType> {
    return this.http.post<IDemandeOffre>(this.resourceUrl, demandeOffre, { observe: 'response' });
  }

  update(demandeOffre: IDemandeOffre): Observable<EntityResponseType> {
    return this.http.put<IDemandeOffre>(`${this.resourceUrl}/${getDemandeOffreIdentifier(demandeOffre) as number}`, demandeOffre, {
      observe: 'response',
    });
  }

  partialUpdate(demandeOffre: IDemandeOffre): Observable<EntityResponseType> {
    return this.http.patch<IDemandeOffre>(`${this.resourceUrl}/${getDemandeOffreIdentifier(demandeOffre) as number}`, demandeOffre, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandeOffre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeOffre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeOffreToCollectionIfMissing(
    demandeOffreCollection: IDemandeOffre[],
    ...demandeOffresToCheck: (IDemandeOffre | null | undefined)[]
  ): IDemandeOffre[] {
    const demandeOffres: IDemandeOffre[] = demandeOffresToCheck.filter(isPresent);
    if (demandeOffres.length > 0) {
      const demandeOffreCollectionIdentifiers = demandeOffreCollection.map(
        demandeOffreItem => getDemandeOffreIdentifier(demandeOffreItem)!
      );
      const demandeOffresToAdd = demandeOffres.filter(demandeOffreItem => {
        const demandeOffreIdentifier = getDemandeOffreIdentifier(demandeOffreItem);
        if (demandeOffreIdentifier == null || demandeOffreCollectionIdentifiers.includes(demandeOffreIdentifier)) {
          return false;
        }
        demandeOffreCollectionIdentifiers.push(demandeOffreIdentifier);
        return true;
      });
      return [...demandeOffresToAdd, ...demandeOffreCollection];
    }
    return demandeOffreCollection;
  }
}
