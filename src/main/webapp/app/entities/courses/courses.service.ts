import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICourses } from 'app/shared/model/courses.model';

type EntityResponseType = HttpResponse<ICourses>;
type EntityArrayResponseType = HttpResponse<ICourses[]>;

@Injectable({ providedIn: 'root' })
export class CoursesService {
  public resourceUrl = SERVER_API_URL + 'api/courses';

  constructor(protected http: HttpClient) {}

  create(courses: ICourses): Observable<EntityResponseType> {
    return this.http.post<ICourses>(this.resourceUrl, courses, { observe: 'response' });
  }

  update(courses: ICourses): Observable<EntityResponseType> {
    return this.http.put<ICourses>(this.resourceUrl, courses, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICourses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICourses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
