import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeOffre } from '../demande-offre.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { DemandeOffreService } from '../service/demande-offre.service';
import { DemandeOffreDeleteDialogComponent } from '../delete/demande-offre-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-demande-offre',
  templateUrl: './demande-offre.component.html',
})
export class DemandeOffreComponent implements OnInit {
  demandeOffres: IDemandeOffre[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected demandeOffreService: DemandeOffreService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.demandeOffres = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.demandeOffreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDemandeOffre[]>) => {
          this.isLoading = false;
          this.paginateDemandeOffres(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.demandeOffres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDemandeOffre): number {
    return item.id!;
  }

  delete(demandeOffre: IDemandeOffre): void {
    const modalRef = this.modalService.open(DemandeOffreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.demandeOffre = demandeOffre;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDemandeOffres(data: IDemandeOffre[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.demandeOffres.push(d);
      }
    }
  }
}
