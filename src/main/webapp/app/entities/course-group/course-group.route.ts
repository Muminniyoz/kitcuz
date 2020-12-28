import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourseGroup, CourseGroup } from 'app/shared/model/course-group.model';
import { CourseGroupService } from './course-group.service';
import { CourseGroupComponent } from './course-group.component';
import { CourseGroupDetailComponent } from './course-group-detail.component';
import { CourseGroupUpdateComponent } from './course-group-update.component';

@Injectable({ providedIn: 'root' })
export class CourseGroupResolve implements Resolve<ICourseGroup> {
  constructor(private service: CourseGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourseGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courseGroup: HttpResponse<CourseGroup>) => {
          if (courseGroup.body) {
            return of(courseGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourseGroup());
  }
}

export const courseGroupRoute: Routes = [
  {
    path: '',
    component: CourseGroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourseGroupDetailComponent,
    resolve: {
      courseGroup: CourseGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourseGroupUpdateComponent,
    resolve: {
      courseGroup: CourseGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourseGroupUpdateComponent,
    resolve: {
      courseGroup: CourseGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.courseGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
