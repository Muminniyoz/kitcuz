import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudentGroup } from 'app/shared/model/student-group.model';

type EntityResponseType = HttpResponse<IStudentGroup>;
type EntityArrayResponseType = HttpResponse<IStudentGroup[]>;

@Injectable({ providedIn: 'root' })
export class StudentGroupService {
  public resourceUrl = SERVER_API_URL + 'api/student-groups';

  constructor(protected http: HttpClient) {}

  create(studentGroup: IStudentGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentGroup);
    return this.http
      .post<IStudentGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentGroup: IStudentGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentGroup);
    return this.http
      .put<IStudentGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(studentGroup: IStudentGroup): IStudentGroup {
    const copy: IStudentGroup = Object.assign({}, studentGroup, {
      startingDate:
        studentGroup.startingDate && studentGroup.startingDate.isValid() ? studentGroup.startingDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startingDate = res.body.startingDate ? moment(res.body.startingDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentGroup: IStudentGroup) => {
        studentGroup.startingDate = studentGroup.startingDate ? moment(studentGroup.startingDate) : undefined;
      });
    }
    return res;
  }
}
