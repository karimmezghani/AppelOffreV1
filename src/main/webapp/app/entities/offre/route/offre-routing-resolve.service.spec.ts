jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOffre, Offre } from '../offre.model';
import { OffreService } from '../service/offre.service';

import { OffreRoutingResolveService } from './offre-routing-resolve.service';

describe('Service Tests', () => {
  describe('Offre routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OffreRoutingResolveService;
    let service: OffreService;
    let resultOffre: IOffre | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OffreRoutingResolveService);
      service = TestBed.inject(OffreService);
      resultOffre = undefined;
    });

    describe('resolve', () => {
      it('should return IOffre returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOffre).toEqual({ id: 123 });
      });

      it('should return new IOffre if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffre = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOffre).toEqual(new Offre());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOffre).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
