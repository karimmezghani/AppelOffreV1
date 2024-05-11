import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAppelOffre, AppelOffre } from '../appel-offre.model';

import { AppelOffreService } from './appel-offre.service';

describe('Service Tests', () => {
  describe('AppelOffre Service', () => {
    let service: AppelOffreService;
    let httpMock: HttpTestingController;
    let elemDefault: IAppelOffre;
    let expectedResult: IAppelOffre | IAppelOffre[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AppelOffreService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        numero: 'AAAAAAA',
        dateDebut: currentDate,
        dateFin: currentDate,
        exercice: 'AAAAAAA',
        designation: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AppelOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.create(new AppelOffre()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AppelOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            numero: 'BBBBBB',
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
            exercice: 'BBBBBB',
            designation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AppelOffre', () => {
        const patchObject = Object.assign(
          {
            numero: 'BBBBBB',
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
          },
          new AppelOffre()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AppelOffre', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            numero: 'BBBBBB',
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
            exercice: 'BBBBBB',
            designation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AppelOffre', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAppelOffreToCollectionIfMissing', () => {
        it('should add a AppelOffre to an empty array', () => {
          const appelOffre: IAppelOffre = { id: 123 };
          expectedResult = service.addAppelOffreToCollectionIfMissing([], appelOffre);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(appelOffre);
        });

        it('should not add a AppelOffre to an array that contains it', () => {
          const appelOffre: IAppelOffre = { id: 123 };
          const appelOffreCollection: IAppelOffre[] = [
            {
              ...appelOffre,
            },
            { id: 456 },
          ];
          expectedResult = service.addAppelOffreToCollectionIfMissing(appelOffreCollection, appelOffre);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AppelOffre to an array that doesn't contain it", () => {
          const appelOffre: IAppelOffre = { id: 123 };
          const appelOffreCollection: IAppelOffre[] = [{ id: 456 }];
          expectedResult = service.addAppelOffreToCollectionIfMissing(appelOffreCollection, appelOffre);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(appelOffre);
        });

        it('should add only unique AppelOffre to an array', () => {
          const appelOffreArray: IAppelOffre[] = [{ id: 123 }, { id: 456 }, { id: 89400 }];
          const appelOffreCollection: IAppelOffre[] = [{ id: 123 }];
          expectedResult = service.addAppelOffreToCollectionIfMissing(appelOffreCollection, ...appelOffreArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const appelOffre: IAppelOffre = { id: 123 };
          const appelOffre2: IAppelOffre = { id: 456 };
          expectedResult = service.addAppelOffreToCollectionIfMissing([], appelOffre, appelOffre2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(appelOffre);
          expect(expectedResult).toContain(appelOffre2);
        });

        it('should accept null and undefined values', () => {
          const appelOffre: IAppelOffre = { id: 123 };
          expectedResult = service.addAppelOffreToCollectionIfMissing([], null, appelOffre, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(appelOffre);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
