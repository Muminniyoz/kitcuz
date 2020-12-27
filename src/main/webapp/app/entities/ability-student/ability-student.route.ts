import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAbilityStudent, AbilityStudent } from 'app/shared/model/ability-student.model';
import { AbilityStudentService } from './ability-student.service';
import { AbilityStudentComponent } from './ability-student.component';
import { AbilityStudentDetailComponent } from './ability-student-detail.component';
import { AbilityStudentUpdateComponent } from './ability-student-update.component';

@Injectable({ providedIn: 'root' })
export class AbilityStudentResolve implements Resolve<IAbilityStudent> {
  constructor(private service: AbilityStudentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAbilityStudent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((abilityStudent: HttpResponse<AbilityStudent>) => {
          if (abilityStudent.body) {
            return of(abilityStudent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AbilityStudent());
  }
}

export const abilityStudentRoute: Routes = [
  {
    path: '',
    component: AbilityStudentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.abilityStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AbilityStudentDetailComponent,
    resolve: {
      abilityStudent: AbilityStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.abilityStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AbilityStudentUpdateComponent,
    resolve: {
      abilityStudent: AbilityStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.abilityStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AbilityStudentUpdateComponent,
    resolve: {
      abilityStudent: AbilityStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.abilityStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
