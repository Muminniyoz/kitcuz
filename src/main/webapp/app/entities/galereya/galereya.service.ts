import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGalereya } from 'app/shared/model/galereya.model';

type EntityResponseType = HttpResponse<IGalereya>;
type EntityArrayResponseType = HttpResponse<IGalereya[]>;

@Injectable({ providedIn: 'root' })
export class GalereyaService {
  public resourceUrl = SERVER_API_URL + 'api/galereyas';

  constructor(protected http: HttpClient) {}

  create(galereya: IGalereya): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(galereya);
    return this.http
      .post<IGalereya>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(galereya: IGalereya): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(galereya);
    return this.http
      .put<IGalereya>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGalereya>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGalereya[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(galereya: IGalereya): IGalereya {
    const copy: IGalereya = Object.assign({}, galereya, {
      createdDate: galereya.createdDate && galereya.createdDate.isValid() ? galereya.createdDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((galereya: IGalereya) => {
        galereya.createdDate = galereya.createdDate ? moment(galereya.createdDate) : undefined;
      });
    }
    return res;
  }
}
