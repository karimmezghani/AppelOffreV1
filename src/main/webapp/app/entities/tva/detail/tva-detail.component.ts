import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITva } from '../tva.model';

@Component({
  selector: 'jhi-tva-detail',
  templateUrl: './tva-detail.component.html',
})
export class TvaDetailComponent implements OnInit {
  tva: ITva | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tva }) => {
      this.tva = tva;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
