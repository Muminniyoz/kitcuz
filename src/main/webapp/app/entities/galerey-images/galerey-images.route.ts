import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGalereyImages, GalereyImages } from 'app/shared/model/galerey-images.model';
import { GalereyImagesService } from './galerey-images.service';
import { GalereyImagesComponent } from './galerey-images.component';
import { GalereyImagesDetailComponent } from './galerey-images-detail.component';
import { GalereyImagesUpdateComponent } from './galerey-images-update.component';

@Injectable({ providedIn: 'root' })
export class GalereyImagesResolve implements Resolve<IGalereyImages> {
  constructor(private service: GalereyImagesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGalereyImages> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((galereyImages: HttpResponse<GalereyImages>) => {
          if (galereyImages.body) {
            return of(galereyImages.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GalereyImages());
  }
}

export const galereyImagesRoute: Routes = [
  {
    path: '',
    component: GalereyImagesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereyImages.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GalereyImagesDetailComponent,
    resolve: {
      galereyImages: GalereyImagesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereyImages.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GalereyImagesUpdateComponent,
    resolve: {
      galereyImages: GalereyImagesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereyImages.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GalereyImagesUpdateComponent,
    resolve: {
      galereyImages: GalereyImagesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.galereyImages.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
