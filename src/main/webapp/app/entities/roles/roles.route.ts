import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRoles, Roles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';
import { RolesComponent } from './roles.component';
import { RolesDetailComponent } from './roles-detail.component';
import { RolesUpdateComponent } from './roles-update.component';

@Injectable({ providedIn: 'root' })
export class RolesResolve implements Resolve<IRoles> {
  constructor(private service: RolesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoles> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((roles: HttpResponse<Roles>) => {
          if (roles.body) {
            return of(roles.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Roles());
  }
}

export const rolesRoute: Routes = [
  {
    path: '',
    component: RolesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.roles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RolesDetailComponent,
    resolve: {
      roles: RolesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.roles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RolesUpdateComponent,
    resolve: {
      roles: RolesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.roles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RolesUpdateComponent,
    resolve: {
      roles: RolesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myblogApp.roles.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
