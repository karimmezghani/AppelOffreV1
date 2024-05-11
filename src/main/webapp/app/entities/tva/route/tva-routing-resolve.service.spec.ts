jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITva, Tva } from '../tva.model';
import { TvaService } from '../service/tva.service';

import { TvaRoutingResolveService } from './tva-routing-resolve.service';

describe('Service Tests', () => {
  describe('Tva routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TvaRoutingResolveService;
    let service: TvaService;
    let resultTva: ITva | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TvaRoutingResolveService);
      service = TestBed.inject(TvaService);
      resultTva = undefined;
    });

    describe('resolve', () => {
      it('should return ITva returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTva = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTva).toEqual({ id: 123 });
      });

      it('should return new ITva if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTva = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTva).toEqual(new Tva());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTva = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTva).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
