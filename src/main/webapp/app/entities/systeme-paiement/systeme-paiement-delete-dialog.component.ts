import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemePaiement } from 'app/shared/model/systeme-paiement.model';
import { SystemePaiementService } from './systeme-paiement.service';

@Component({
  templateUrl: './systeme-paiement-delete-dialog.component.html',
})
export class SystemePaiementDeleteDialogComponent {
  systemePaiement?: ISystemePaiement;

  constructor(
    protected systemePaiementService: SystemePaiementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.systemePaiementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('systemePaiementListModification');
      this.activeModal.close();
    });
  }
}
