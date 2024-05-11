import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppelOffre, getAppelOffreIdentifier } from '../appel-offre.model';

export type EntityResponseType = HttpResponse<IAppelOffre>;
export type EntityArrayResponseType = HttpResponse<IAppelOffre[]>;

@Injectable({ providedIn: 'root' })
export class AppelOffreService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/appel-offres');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(appelOffre: IAppelOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appelOffre);
    return this.http
      .post<IAppelOffre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(appelOffre: IAppelOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appelOffre);
    return this.http
      .put<IAppelOffre>(`${this.resourceUrl}/${getAppelOffreIdentifier(appelOffre) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(appelOffre: IAppelOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appelOffre);
    return this.http
      .patch<IAppelOffre>(`${this.resourceUrl}/${getAppelOffreIdentifier(appelOffre) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAppelOffre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAppelOffre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAppelOffreToCollectionIfMissing(
    appelOffreCollection: IAppelOffre[],
    ...appelOffresToCheck: (IAppelOffre | null | undefined)[]
  ): IAppelOffre[] {
    const appelOffres: IAppelOffre[] = appelOffresToCheck.filter(isPresent);
    if (appelOffres.length > 0) {
      const appelOffreCollectionIdentifiers = appelOffreCollection.map(appelOffreItem => getAppelOffreIdentifier(appelOffreItem)!);
      const appelOffresToAdd = appelOffres.filter(appelOffreItem => {
        const appelOffreIdentifier = getAppelOffreIdentifier(appelOffreItem);
        if (appelOffreIdentifier == null || appelOffreCollectionIdentifiers.includes(appelOffreIdentifier)) {
          return false;
        }
        appelOffreCollectionIdentifiers.push(appelOffreIdentifier);
        return true;
      });
      return [...appelOffresToAdd, ...appelOffreCollection];
    }
    return appelOffreCollection;
  }

  protected convertDateFromClient(appelOffre: IAppelOffre): IAppelOffre {
    return Object.assign({}, appelOffre, {
      dateDebut: appelOffre.dateDebut?.isValid() ? appelOffre.dateDebut.toJSON() : undefined,
      dateFin: appelOffre.dateFin?.isValid() ? appelOffre.dateFin.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((appelOffre: IAppelOffre) => {
        appelOffre.dateDebut = appelOffre.dateDebut ? dayjs(appelOffre.dateDebut) : undefined;
        appelOffre.dateFin = appelOffre.dateFin ? dayjs(appelOffre.dateFin) : undefined;
      });
    }
    return res;
  }
}
