import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourses, Courses } from 'app/shared/model/courses.model';
import { CoursesService } from './courses.service';
import { CoursesComponent } from './courses.component';
import { CoursesDetailComponent } from './courses-detail.component';
import { CoursesUpdateComponent } from './courses-update.component';

@Injectable({ providedIn: 'root' })
export class CoursesResolve implements Resolve<ICourses> {
  constructor(private service: CoursesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courses: HttpResponse<Courses>) => {
          if (courses.body) {
            return of(courses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Courses());
  }
}

export const coursesRoute: Routes = [
  {
    path: '',
    component: CoursesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courses.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CoursesDetailComponent,
    resolve: {
      courses: CoursesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courses.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CoursesUpdateComponent,
    resolve: {
      courses: CoursesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courses.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CoursesUpdateComponent,
    resolve: {
      courses: CoursesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courses.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
