import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanning, Planning } from 'app/shared/model/planning.model';
import { PlanningService } from './planning.service';
import { PlanningComponent } from './planning.component';
import { PlanningDetailComponent } from './planning-detail.component';
import { PlanningUpdateComponent } from './planning-update.component';

@Injectable({ providedIn: 'root' })
export class PlanningResolve implements Resolve<IPlanning> {
  constructor(private service: PlanningService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanning> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planning: HttpResponse<Planning>) => {
          if (planning.body) {
            return of(planning.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Planning());
  }
}

export const planningRoute: Routes = [
  {
    path: '',
    component: PlanningComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.planning.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanningDetailComponent,
    resolve: {
      planning: PlanningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.planning.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanningUpdateComponent,
    resolve: {
      planning: PlanningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.planning.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanningUpdateComponent,
    resolve: {
      planning: PlanningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.planning.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
