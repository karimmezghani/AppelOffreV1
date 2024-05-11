jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDemandeOffre, DemandeOffre } from '../demande-offre.model';
import { DemandeOffreService } from '../service/demande-offre.service';

import { DemandeOffreRoutingResolveService } from './demande-offre-routing-resolve.service';

describe('Service Tests', () => {
  describe('DemandeOffre routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DemandeOffreRoutingResolveService;
    let service: DemandeOffreService;
    let resultDemandeOffre: IDemandeOffre | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DemandeOffreRoutingResolveService);
      service = TestBed.inject(DemandeOffreService);
      resultDemandeOffre = undefined;
    });

    describe('resolve', () => {
      it('should return IDemandeOffre returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandeOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandeOffre).toEqual({ id: 123 });
      });

      it('should return new IDemandeOffre if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandeOffre = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDemandeOffre).toEqual(new DemandeOffre());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandeOffre = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandeOffre).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
