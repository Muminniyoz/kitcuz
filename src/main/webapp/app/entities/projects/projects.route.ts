import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjects, Projects } from 'app/shared/model/projects.model';
import { ProjectsService } from './projects.service';
import { ProjectsComponent } from './projects.component';
import { ProjectsDetailComponent } from './projects-detail.component';
import { ProjectsUpdateComponent } from './projects-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectsResolve implements Resolve<IProjects> {
  constructor(private service: ProjectsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjects> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projects: HttpResponse<Projects>) => {
          if (projects.body) {
            return of(projects.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Projects());
  }
}

export const projectsRoute: Routes = [
  {
    path: '',
    component: ProjectsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.projects.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectsDetailComponent,
    resolve: {
      projects: ProjectsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.projects.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectsUpdateComponent,
    resolve: {
      projects: ProjectsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.projects.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectsUpdateComponent,
    resolve: {
      projects: ProjectsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.projects.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
