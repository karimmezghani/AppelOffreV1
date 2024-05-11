import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fournisseur',
        data: { pageTitle: 'appelOffreApp.fournisseur.home.title' },
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.FournisseurModule),
      },
      {
        path: 'offre',
        data: { pageTitle: 'appelOffreApp.offre.home.title' },
        loadChildren: () => import('./offre/offre.module').then(m => m.OffreModule),
      },
      {
        path: 'tva',
        data: { pageTitle: 'appelOffreApp.tva.home.title' },
        loadChildren: () => import('./tva/tva.module').then(m => m.TvaModule),
      },
      {
        path: 'demande-offre',
        data: { pageTitle: 'appelOffreApp.demandeOffre.home.title' },
        loadChildren: () => import('./demande-offre/demande-offre.module').then(m => m.DemandeOffreModule),
      },
      {
        path: 'appel-offre',
        data: { pageTitle: 'appelOffreApp.appelOffre.home.title' },
        loadChildren: () => import('./appel-offre/appel-offre.module').then(m => m.AppelOffreModule),
      },
      {
        path: 'article',
        data: { pageTitle: 'appelOffreApp.article.home.title' },
        loadChildren: () => import('./article/article.module').then(m => m.ArticleModule),
      },
      {
        path: 'categorie',
        data: { pageTitle: 'appelOffreApp.categorie.home.title' },
        loadChildren: () => import('./categorie/categorie.module').then(m => m.CategorieModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
