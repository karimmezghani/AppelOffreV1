jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAppelOffre, AppelOffre } from '../appel-offre.model';
import { AppelOffreService } from '../service/appel-offre.service';

import { AppelOffreRoutingResolveService } from './appel-offre-routing-resolve.service';

describe('Service Tests', () => {
  describe('AppelOffre routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AppelOffreRoutingResolveService;
    let service: AppelOffreService;
    let resultAppelOffre: IAppelOffre | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AppelOffreRoutingResolveService);
      service = TestBed.inject(AppelOffreService);
      resultAppelOffre = undefined;
    });

    describe('resolve', () => {
      it('should return IAppelOffre returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAppelOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAppelOffre).toEqual({ id: 123 });
      });

      it('should return new IAppelOffre if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAppelOffre = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAppelOffre).toEqual(new AppelOffre());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAppelOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAppelOffre).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
