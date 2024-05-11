import { IOffre } from 'app/entities/offre/offre.model';
import { IArticle } from 'app/entities/article/article.model';
import { IAppelOffre } from 'app/entities/appel-offre/appel-offre.model';

export interface IDemandeOffre {
  id?: number;
  description?: string;
  quantite?: number;
  offres?: IOffre[] | null;
  articles?: IArticle[] | null;
  appelOffre?: IAppelOffre | null;
}

export class DemandeOffre implements IDemandeOffre {
  constructor(
    public id?: number,
    public description?: string,
    public quantite?: number,
    public offres?: IOffre[] | null,
    public articles?: IArticle[] | null,
    public appelOffre?: IAppelOffre | null
  ) {}
}

export function getDemandeOffreIdentifier(demandeOffre: IDemandeOffre): number | undefined {
  return demandeOffre.id;
}
