import { IDemandeOffre } from 'app/entities/demande-offre/demande-offre.model';
import { ICategorie } from 'app/entities/categorie/categorie.model';

export interface IArticle {
  id?: number;
  designation?: string;
  actif?: boolean | null;
  codeSaisi?: string;
  demandeOffre?: IDemandeOffre | null;
  categorie?: ICategorie | null;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public designation?: string,
    public actif?: boolean | null,
    public codeSaisi?: string,
    public demandeOffre?: IDemandeOffre | null,
    public categorie?: ICategorie | null
  ) {
    this.actif = this.actif ?? false;
  }
}

export function getArticleIdentifier(article: IArticle): number | undefined {
  return article.id;
}
