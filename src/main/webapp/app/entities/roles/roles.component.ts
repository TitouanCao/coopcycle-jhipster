import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';
import { RolesDeleteDialogComponent } from './roles-delete-dialog.component';

@Component({
  selector: 'jhi-roles',
  templateUrl: './roles.component.html',
})
export class RolesComponent implements OnInit, OnDestroy {
  roles?: IRoles[];
  eventSubscriber?: Subscription;

  constructor(protected rolesService: RolesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.rolesService.query().subscribe((res: HttpResponse<IRoles[]>) => (this.roles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRoles): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('rolesListModification', () => this.loadAll());
  }

  delete(roles: IRoles): void {
    const modalRef = this.modalService.open(RolesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roles = roles;
  }
}
