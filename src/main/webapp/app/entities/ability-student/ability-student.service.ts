import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAbilityStudent } from 'app/shared/model/ability-student.model';

type EntityResponseType = HttpResponse<IAbilityStudent>;
type EntityArrayResponseType = HttpResponse<IAbilityStudent[]>;

@Injectable({ providedIn: 'root' })
export class AbilityStudentService {
  public resourceUrl = SERVER_API_URL + 'api/ability-students';

  constructor(protected http: HttpClient) {}

  create(abilityStudent: IAbilityStudent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abilityStudent);
    return this.http
      .post<IAbilityStudent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(abilityStudent: IAbilityStudent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abilityStudent);
    return this.http
      .put<IAbilityStudent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAbilityStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAbilityStudent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(abilityStudent: IAbilityStudent): IAbilityStudent {
    const copy: IAbilityStudent = Object.assign({}, abilityStudent, {
      dateOfBirth:
        abilityStudent.dateOfBirth && abilityStudent.dateOfBirth.isValid() ? abilityStudent.dateOfBirth.format(DATE_FORMAT) : undefined,
      registerationDate:
        abilityStudent.registerationDate && abilityStudent.registerationDate.isValid()
          ? abilityStudent.registerationDate.format(DATE_FORMAT)
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
      res.body.forEach((abilityStudent: IAbilityStudent) => {
        abilityStudent.dateOfBirth = abilityStudent.dateOfBirth ? moment(abilityStudent.dateOfBirth) : undefined;
        abilityStudent.registerationDate = abilityStudent.registerationDate ? moment(abilityStudent.registerationDate) : undefined;
      });
    }
    return res;
  }
}
