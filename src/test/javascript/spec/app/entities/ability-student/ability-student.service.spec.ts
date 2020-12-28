import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AbilityStudentService } from 'app/entities/ability-student/ability-student.service';
import { IAbilityStudent, AbilityStudent } from 'app/shared/model/ability-student.model';

describe('Service Tests', () => {
  describe('AbilityStudent Service', () => {
    let injector: TestBed;
    let service: AbilityStudentService;
    let httpMock: HttpTestingController;
    let elemDefault: IAbilityStudent;
    let expectedResult: IAbilityStudent | IAbilityStudent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AbilityStudentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AbilityStudent(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AbilityStudent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
          },
          returnedFromService
        );

        service.create(new AbilityStudent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AbilityStudent', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            about: 'BBBBBB',
            email: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
            telephone: 'BBBBBB',
            mobile: 'BBBBBB',
            thumbnailPhotoUrl: 'BBBBBB',
            fullPhotoUrl: 'BBBBBB',
            isShowing: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AbilityStudent', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            about: 'BBBBBB',
            email: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
            telephone: 'BBBBBB',
            mobile: 'BBBBBB',
            thumbnailPhotoUrl: 'BBBBBB',
            fullPhotoUrl: 'BBBBBB',
            isShowing: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AbilityStudent', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
