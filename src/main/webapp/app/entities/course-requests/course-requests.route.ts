import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourseRequests, CourseRequests } from 'app/shared/model/course-requests.model';
import { CourseRequestsService } from './course-requests.service';
import { CourseRequestsComponent } from './course-requests.component';
import { CourseRequestsDetailComponent } from './course-requests-detail.component';
import { CourseRequestsUpdateComponent } from './course-requests-update.component';

@Injectable({ providedIn: 'root' })
export class CourseRequestsResolve implements Resolve<ICourseRequests> {
  constructor(private service: CourseRequestsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourseRequests> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courseRequests: HttpResponse<CourseRequests>) => {
          if (courseRequests.body) {
            return of(courseRequests.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourseRequests());
  }
}

export const courseRequestsRoute: Routes = [
  {
    path: '',
    component: CourseRequestsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseRequests.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourseRequestsDetailComponent,
    resolve: {
      courseRequests: CourseRequestsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseRequests.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourseRequestsUpdateComponent,
    resolve: {
      courseRequests: CourseRequestsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseRequests.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourseRequestsUpdateComponent,
    resolve: {
      courseRequests: CourseRequestsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseRequests.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
