import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandeOffre, DemandeOffre } from '../demande-offre.model';

import { DemandeOffreService } from './demande-offre.service';

describe('Service Tests', () => {
  describe('DemandeOffre Service', () => {
    let service: DemandeOffreService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandeOffre;
    let expectedResult: IDemandeOffre | IDemandeOffre[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandeOffreService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        description: 'AAAAAAA',
        quantite: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DemandeOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DemandeOffre()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandeOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            description: 'BBBBBB',
            quantite: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DemandeOffre', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
          },
          new DemandeOffre()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandeOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            description: 'BBBBBB',
            quantite: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DemandeOffre', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandeOffreToCollectionIfMissing', () => {
        it('should add a DemandeOffre to an empty array', () => {
          const demandeOffre: IDemandeOffre = { id: 123 };
          expectedResult = service.addDemandeOffreToCollectionIfMissing([], demandeOffre);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandeOffre);
        });

        it('should not add a DemandeOffre to an array that contains it', () => {
          const demandeOffre: IDemandeOffre = { id: 123 };
          const demandeOffreCollection: IDemandeOffre[] = [
            {
              ...demandeOffre,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandeOffreToCollectionIfMissing(demandeOffreCollection, demandeOffre);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandeOffre to an array that doesn't contain it", () => {
          const demandeOffre: IDemandeOffre = { id: 123 };
          const demandeOffreCollection: IDemandeOffre[] = [{ id: 456 }];
          expectedResult = service.addDemandeOffreToCollectionIfMissing(demandeOffreCollection, demandeOffre);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandeOffre);
        });

        it('should add only unique DemandeOffre to an array', () => {
          const demandeOffreArray: IDemandeOffre[] = [{ id: 123 }, { id: 456 }, { id: 8300 }];
          const demandeOffreCollection: IDemandeOffre[] = [{ id: 123 }];
          expectedResult = service.addDemandeOffreToCollectionIfMissing(demandeOffreCollection, ...demandeOffreArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandeOffre: IDemandeOffre = { id: 123 };
          const demandeOffre2: IDemandeOffre = { id: 456 };
          expectedResult = service.addDemandeOffreToCollectionIfMissing([], demandeOffre, demandeOffre2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandeOffre);
          expect(expectedResult).toContain(demandeOffre2);
        });

        it('should accept null and undefined values', () => {
          const demandeOffre: IDemandeOffre = { id: 123 };
          expectedResult = service.addDemandeOffreToCollectionIfMissing([], null, demandeOffre, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandeOffre);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
