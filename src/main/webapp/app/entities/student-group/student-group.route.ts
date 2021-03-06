import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudentGroup, StudentGroup } from 'app/shared/model/student-group.model';
import { StudentGroupService } from './student-group.service';
import { StudentGroupComponent } from './student-group.component';
import { StudentGroupDetailComponent } from './student-group-detail.component';
import { StudentGroupUpdateComponent } from './student-group-update.component';

@Injectable({ providedIn: 'root' })
export class StudentGroupResolve implements Resolve<IStudentGroup> {
  constructor(private service: StudentGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStudentGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((studentGroup: HttpResponse<StudentGroup>) => {
          if (studentGroup.body) {
            return of(studentGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StudentGroup());
  }
}

export const studentGroupRoute: Routes = [
  {
    path: '',
    component: StudentGroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.studentGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StudentGroupDetailComponent,
    resolve: {
      studentGroup: StudentGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.studentGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StudentGroupUpdateComponent,
    resolve: {
      studentGroup: StudentGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.studentGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StudentGroupUpdateComponent,
    resolve: {
      studentGroup: StudentGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.studentGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
