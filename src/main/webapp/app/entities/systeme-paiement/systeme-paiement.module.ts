import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyblogSharedModule } from 'app/shared/shared.module';
import { SystemePaiementComponent } from './systeme-paiement.component';
import { SystemePaiementDetailComponent } from './systeme-paiement-detail.component';
import { SystemePaiementUpdateComponent } from './systeme-paiement-update.component';
import { SystemePaiementDeleteDialogComponent } from './systeme-paiement-delete-dialog.component';
import { systemePaiementRoute } from './systeme-paiement.route';

@NgModule({
  imports: [MyblogSharedModule, RouterModule.forChild(systemePaiementRoute)],
  declarations: [
    SystemePaiementComponent,
    SystemePaiementDetailComponent,
    SystemePaiementUpdateComponent,
    SystemePaiementDeleteDialogComponent,
  ],
  entryComponents: [SystemePaiementDeleteDialogComponent],
})
export class MyblogSystemePaiementModule {}
