import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICourseRequests } from 'app/shared/model/course-requests.model';

type EntityResponseType = HttpResponse<ICourseRequests>;
type EntityArrayResponseType = HttpResponse<ICourseRequests[]>;

@Injectable({ providedIn: 'root' })
export class CourseRequestsService {
  public resourceUrl = SERVER_API_URL + 'api/course-requests';

  constructor(protected http: HttpClient) {}

  create(courseRequests: ICourseRequests): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courseRequests);
    return this.http
      .post<ICourseRequests>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(courseRequests: ICourseRequests): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courseRequests);
    return this.http
      .put<ICourseRequests>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICourseRequests>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICourseRequests[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(courseRequests: ICourseRequests): ICourseRequests {
    const copy: ICourseRequests = Object.assign({}, courseRequests, {
      dateOfBirth:
        courseRequests.dateOfBirth && courseRequests.dateOfBirth.isValid() ? courseRequests.dateOfBirth.format(DATE_FORMAT) : undefined,
      registerationDate:
        courseRequests.registerationDate && courseRequests.registerationDate.isValid()
          ? courseRequests.registerationDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
      res.body.registerationDate = res.body.registerationDate ? moment(res.body.registerationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((courseRequests: ICourseRequests) => {
        courseRequests.dateOfBirth = courseRequests.dateOfBirth ? moment(courseRequests.dateOfBirth) : undefined;
        courseRequests.registerationDate = courseRequests.registerationDate ? moment(courseRequests.registerationDate) : undefined;
      });
    }
    return res;
  }
}
