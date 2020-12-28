import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISystemFiles, SystemFiles } from 'app/shared/model/system-files.model';
import { SystemFilesService } from './system-files.service';
import { SystemFilesComponent } from './system-files.component';
import { SystemFilesDetailComponent } from './system-files-detail.component';
import { SystemFilesUpdateComponent } from './system-files-update.component';

@Injectable({ providedIn: 'root' })
export class SystemFilesResolve implements Resolve<ISystemFiles> {
  constructor(private service: SystemFilesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISystemFiles> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((systemFiles: HttpResponse<SystemFiles>) => {
          if (systemFiles.body) {
            return of(systemFiles.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SystemFiles());
  }
}

export const systemFilesRoute: Routes = [
  {
    path: '',
    component: SystemFilesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.systemFiles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SystemFilesDetailComponent,
    resolve: {
      systemFiles: SystemFilesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.systemFiles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SystemFilesUpdateComponent,
    resolve: {
      systemFiles: SystemFilesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.systemFiles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SystemFilesUpdateComponent,
    resolve: {
      systemFiles: SystemFilesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.systemFiles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
