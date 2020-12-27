import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentGroupService } from 'app/entities/student-group/student-group.service';
import { IStudentGroup, StudentGroup } from 'app/shared/model/student-group.model';

describe('Service Tests', () => {
  describe('StudentGroup Service', () => {
    let injector: TestBed;
    let service: StudentGroupService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudentGroup;
    let expectedResult: IStudentGroup | IStudentGroup[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudentGroupService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StudentGroup(0, currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startingDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StudentGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startingDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
          },
          returnedFromService
        );

        service.create(new StudentGroup()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StudentGroup', () => {
        const returnedFromService = Object.assign(
          {
            startingDate: currentDate.format(DATE_FORMAT),
            active: true,
            contractNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StudentGroup', () => {
        const returnedFromService = Object.assign(
          {
            startingDate: currentDate.format(DATE_FORMAT),
            active: true,
            contractNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StudentGroup', () => {
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
