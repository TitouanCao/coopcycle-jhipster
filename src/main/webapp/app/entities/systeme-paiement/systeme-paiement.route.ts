import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISystemePaiement, SystemePaiement } from 'app/shared/model/systeme-paiement.model';
import { SystemePaiementService } from './systeme-paiement.service';
import { SystemePaiementComponent } from './systeme-paiement.component';
import { SystemePaiementDetailComponent } from './systeme-paiement-detail.component';
import { SystemePaiementUpdateComponent } from './systeme-paiement-update.component';

@Injectable({ providedIn: 'root' })
export class SystemePaiementResolve implements Resolve<ISystemePaiement> {
  constructor(private service: SystemePaiementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISystemePaiement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((systemePaiement: HttpResponse<SystemePaiement>) => {
          if (systemePaiement.body) {
            return of(systemePaiement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SystemePaiement());
  }
}

export const systemePaiementRoute: Routes = [
  {
    path: '',
    component: SystemePaiementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.systemePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SystemePaiementDetailComponent,
    resolve: {
      systemePaiement: SystemePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.systemePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SystemePaiementUpdateComponent,
    resolve: {
      systemePaiement: SystemePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.systemePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SystemePaiementUpdateComponent,
    resolve: {
      systemePaiement: SystemePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.systemePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
