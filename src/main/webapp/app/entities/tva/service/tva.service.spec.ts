import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITva, Tva } from '../tva.model';

import { TvaService } from './tva.service';

describe('Service Tests', () => {
  describe('Tva Service', () => {
    let service: TvaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITva;
    let expectedResult: ITva | ITva[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TvaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tauxTva: 0,
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

      it('should create a Tva', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Tva()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tva', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tauxTva: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Tva', () => {
        const patchObject = Object.assign({}, new Tva());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tva', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tauxTva: 1,
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

      it('should delete a Tva', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTvaToCollectionIfMissing', () => {
        it('should add a Tva to an empty array', () => {
          const tva: ITva = { id: 123 };
          expectedResult = service.addTvaToCollectionIfMissing([], tva);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tva);
        });

        it('should not add a Tva to an array that contains it', () => {
          const tva: ITva = { id: 123 };
          const tvaCollection: ITva[] = [
            {
              ...tva,
            },
            { id: 456 },
          ];
          expectedResult = service.addTvaToCollectionIfMissing(tvaCollection, tva);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Tva to an array that doesn't contain it", () => {
          const tva: ITva = { id: 123 };
          const tvaCollection: ITva[] = [{ id: 456 }];
          expectedResult = service.addTvaToCollectionIfMissing(tvaCollection, tva);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tva);
        });

        it('should add only unique Tva to an array', () => {
          const tvaArray: ITva[] = [{ id: 123 }, { id: 456 }, { id: 26923 }];
          const tvaCollection: ITva[] = [{ id: 123 }];
          expectedResult = service.addTvaToCollectionIfMissing(tvaCollection, ...tvaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const tva: ITva = { id: 123 };
          const tva2: ITva = { id: 456 };
          expectedResult = service.addTvaToCollectionIfMissing([], tva, tva2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tva);
          expect(expectedResult).toContain(tva2);
        });

        it('should accept null and undefined values', () => {
          const tva: ITva = { id: 123 };
          expectedResult = service.addTvaToCollectionIfMissing([], null, tva, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tva);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
