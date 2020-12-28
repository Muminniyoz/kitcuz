import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISystemFiles } from 'app/shared/model/system-files.model';

type EntityResponseType = HttpResponse<ISystemFiles>;
type EntityArrayResponseType = HttpResponse<ISystemFiles[]>;

@Injectable({ providedIn: 'root' })
export class SystemFilesService {
  public resourceUrl = SERVER_API_URL + 'api/system-files';

  constructor(protected http: HttpClient) {}

  create(systemFiles: ISystemFiles): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemFiles);
    return this.http
      .post<ISystemFiles>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(systemFiles: ISystemFiles): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemFiles);
    return this.http
      .put<ISystemFiles>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISystemFiles>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISystemFiles[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(systemFiles: ISystemFiles): ISystemFiles {
    const copy: ISystemFiles = Object.assign({}, systemFiles, {
      time: systemFiles.time && systemFiles.time.isValid() ? systemFiles.time.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time ? moment(res.body.time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((systemFiles: ISystemFiles) => {
        systemFiles.time = systemFiles.time ? moment(systemFiles.time) : undefined;
      });
    }
    return res;
  }
}
