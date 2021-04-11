import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'roles',
        loadChildren: () => import('./roles/roles.module').then(m => m.MyblogRolesModule),
      },
      {
        path: 'compte',
        loadChildren: () => import('./compte/compte.module').then(m => m.MyblogCompteModule),
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.MyblogProduitModule),
      },
      {
        path: 'panier',
        loadChildren: () => import('./panier/panier.module').then(m => m.MyblogPanierModule),
      },
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.MyblogRestaurantModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.MyblogCourseModule),
      },
      {
        path: 'systeme-paiement',
        loadChildren: () => import('./systeme-paiement/systeme-paiement.module').then(m => m.MyblogSystemePaiementModule),
      },
      {
        path: 'cooperative',
        loadChildren: () => import('./cooperative/cooperative.module').then(m => m.MyblogCooperativeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MyblogEntityModule {}
