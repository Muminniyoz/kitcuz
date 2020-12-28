import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGalereya, Galereya } from 'app/shared/model/galereya.model';
import { GalereyaService } from './galereya.service';
import { GalereyaComponent } from './galereya.component';
import { GalereyaDetailComponent } from './galereya-detail.component';
import { GalereyaUpdateComponent } from './galereya-update.component';

@Injectable({ providedIn: 'root' })
export class GalereyaResolve implements Resolve<IGalereya> {
  constructor(private service: GalereyaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGalereya> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((galereya: HttpResponse<Galereya>) => {
          if (galereya.body) {
            return of(galereya.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Galereya());
  }
}

export const galereyaRoute: Routes = [
  {
    path: '',
    component: GalereyaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereya.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GalereyaDetailComponent,
    resolve: {
      galereya: GalereyaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereya.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GalereyaUpdateComponent,
    resolve: {
      galereya: GalereyaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereya.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GalereyaUpdateComponent,
    resolve: {
      galereya: GalereyaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereya.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
