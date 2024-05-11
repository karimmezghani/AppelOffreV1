import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppelOffre } from '../appel-offre.model';

@Component({
  selector: 'jhi-appel-offre-detail',
  templateUrl: './appel-offre-detail.component.html',
})
export class AppelOffreDetailComponent implements OnInit {
  appelOffre: IAppelOffre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appelOffre }) => {
      this.appelOffre = appelOffre;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
