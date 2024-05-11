import { IArticle } from 'app/entities/article/article.model';

export interface ICategorie {
  id?: number;
  designation?: string;
  actif?: boolean | null;
  articles?: IArticle[] | null;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public designation?: string, public actif?: boolean | null, public articles?: IArticle[] | null) {
    this.actif = this.actif ?? false;
  }
}

export function getCategorieIdentifier(categorie: ICategorie): number | undefined {
  return categorie.id;
}
