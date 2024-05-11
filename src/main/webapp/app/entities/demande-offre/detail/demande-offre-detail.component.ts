import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeOffre } from '../demande-offre.model';

@Component({
  selector: 'jhi-demande-offre-detail',
  templateUrl: './demande-offre-detail.component.html',
})
export class DemandeOffreDetailComponent implements OnInit {
  demandeOffre: IDemandeOffre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeOffre }) => {
      this.demandeOffre = demandeOffre;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
