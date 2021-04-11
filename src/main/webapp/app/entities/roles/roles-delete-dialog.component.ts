import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';

@Component({
  templateUrl: './roles-delete-dialog.component.html',
})
export class RolesDeleteDialogComponent {
  roles?: IRoles;

  constructor(protected rolesService: RolesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rolesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rolesListModification');
      this.activeModal.close();
    });
  }
}
