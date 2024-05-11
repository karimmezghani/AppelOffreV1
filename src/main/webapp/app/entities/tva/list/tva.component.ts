import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITva } from '../tva.model';
import { TvaService } from '../service/tva.service';
import { TvaDeleteDialogComponent } from '../delete/tva-delete-dialog.component';

@Component({
  selector: 'jhi-tva',
  templateUrl: './tva.component.html',
})
export class TvaComponent implements OnInit {
  tvas?: ITva[];
  isLoading = false;

  constructor(protected tvaService: TvaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tvaService.query().subscribe(
      (res: HttpResponse<ITva[]>) => {
        this.isLoading = false;
        this.tvas = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITva): number {
    return item.id!;
  }

  delete(tva: ITva): void {
    const modalRef = this.modalService.open(TvaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tva = tva;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
