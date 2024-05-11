import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppelOffre } from '../appel-offre.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { AppelOffreService } from '../service/appel-offre.service';
import { AppelOffreDeleteDialogComponent } from '../delete/appel-offre-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-appel-offre',
  templateUrl: './appel-offre.component.html',
})
export class AppelOffreComponent implements OnInit {
  appelOffres: IAppelOffre[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected appelOffreService: AppelOffreService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.appelOffres = [];
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

    this.appelOffreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IAppelOffre[]>) => {
          this.isLoading = false;
          this.paginateAppelOffres(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.appelOffres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAppelOffre): number {
    return item.id!;
  }

  delete(appelOffre: IAppelOffre): void {
    const modalRef = this.modalService.open(AppelOffreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appelOffre = appelOffre;
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

  protected paginateAppelOffres(data: IAppelOffre[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.appelOffres.push(d);
      }
    }
  }
}
