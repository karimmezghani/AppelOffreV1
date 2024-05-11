import { IOffre } from 'app/entities/offre/offre.model';
import { IUser } from 'app/entities/user/user.model';

export interface IFournisseur {
  id?: number;
  raisonSociale?: string;
  matriculeFiscale?: string;
  adresse?: string;
  email?: string;
  telephone?: number;
  offres?: IOffre[] | null;
  user?: IUser | null;
}

export class Fournisseur implements IFournisseur {
  constructor(
    public id?: number,
    public raisonSociale?: string,
    public matriculeFiscale?: string,
    public adresse?: string,
    public email?: string,
    public telephone?: number,
    public offres?: IOffre[] | null,
    public user?: IUser | null
  ) {}
}

export function getFournisseurIdentifier(fournisseur: IFournisseur): number | undefined {
  return fournisseur.id;
}
