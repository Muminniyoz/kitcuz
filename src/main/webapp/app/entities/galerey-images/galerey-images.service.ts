import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGalereyImages } from 'app/shared/model/galerey-images.model';

type EntityResponseType = HttpResponse<IGalereyImages>;
type EntityArrayResponseType = HttpResponse<IGalereyImages[]>;

@Injectable({ providedIn: 'root' })
export class GalereyImagesService {
  public resourceUrl = SERVER_API_URL + 'api/galerey-images';

  constructor(protected http: HttpClient) {}

  create(galereyImages: IGalereyImages): Observable<EntityResponseType> {
    return this.http.post<IGalereyImages>(this.resourceUrl, galereyImages, { observe: 'response' });
  }

  update(galereyImages: IGalereyImages): Observable<EntityResponseType> {
    return this.http.put<IGalereyImages>(this.resourceUrl, galereyImages, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGalereyImages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGalereyImages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
