import { ITva } from 'app/entities/tva/tva.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';

export interface IOffre {
  id?: number;
  uniteMesure?: string;
  marque?: string;
  prixUnitaire?: number;
  origine?: string;
  delaiLivraison?: number;
  observation?: string | null;
  amc?: boolean | null;
  echantillon?: boolean | null;
  fodec?: boolean | null;
  avecCodeBarre?: boolean | null;
  conditionnement?: string | null;
  prixConditionnement?: number | null;
  tva?: ITva | null;
  fournisseur?: IFournisseur | null;
  demandeOffre?: IDemandeOffre | null;
}

export class Offre implements IOffre {
  constructor(
    public id?: number,
    public uniteMesure?: string,
    public marque?: string,
    public prixUnitaire?: number,
    public origine?: string,
    public delaiLivraison?: number,
    public observation?: string | null,
    public amc?: boolean | null,
    public echantillon?: boolean | null,
    public fodec?: boolean | null,
    public avecCodeBarre?: boolean | null,
    public conditionnement?: string | null,
    public prixConditionnement?: number | null,
    public tva?: ITva | null,
    public fournisseur?: IFournisseur | null,
    public demandeOffre?: IDemandeOffre | null
  ) {
    this.amc = this.amc ?? false;
    this.echantillon = this.echantillon ?? false;
    this.fodec = this.fodec ?? false;
    this.avecCodeBarre = this.avecCodeBarre ?? false;
  }
}

export function getOffreIdentifier(offre: IOffre): number | undefined {
  return offre.id;
}
