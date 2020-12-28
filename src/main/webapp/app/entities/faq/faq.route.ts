import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFaq, Faq } from 'app/shared/model/faq.model';
import { FaqService } from './faq.service';
import { FaqComponent } from './faq.component';
import { FaqDetailComponent } from './faq-detail.component';
import { FaqUpdateComponent } from './faq-update.component';

@Injectable({ providedIn: 'root' })
export class FaqResolve implements Resolve<IFaq> {
  constructor(private service: FaqService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFaq> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((faq: HttpResponse<Faq>) => {
          if (faq.body) {
            return of(faq.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Faq());
  }
}

export const faqRoute: Routes = [
  {
    path: '',
    component: FaqComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.faq.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FaqDetailComponent,
    resolve: {
      faq: FaqResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.faq.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FaqUpdateComponent,
    resolve: {
      faq: FaqResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.faq.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FaqUpdateComponent,
    resolve: {
      faq: FaqResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kitcuzApp.faq.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
