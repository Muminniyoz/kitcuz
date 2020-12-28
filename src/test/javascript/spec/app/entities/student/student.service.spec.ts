import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent, Student } from 'app/shared/model/student.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('Student Service', () => {
    let injector: TestBed;
    let service: StudentService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudent;
    let expectedResult: IStudent | IStudent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Student(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        Gender.MALE,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Student', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
            registerationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.create(new Student()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Student', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            email: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            gender: 'BBBBBB',
            registerationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_TIME_FORMAT),
            telephone: 'BBBBBB',
            mobile: 'BBBBBB',
            thumbnailPhotoUrl: 'BBBBBB',
            fullPhotoUrl: 'BBBBBB',
            active: true,
            key: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Student', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            email: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            gender: 'BBBBBB',
            registerationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_TIME_FORMAT),
            telephone: 'BBBBBB',
            mobile: 'BBBBBB',
            thumbnailPhotoUrl: 'BBBBBB',
            fullPhotoUrl: 'BBBBBB',
            active: true,
            key: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            registerationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Student', () => {
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
