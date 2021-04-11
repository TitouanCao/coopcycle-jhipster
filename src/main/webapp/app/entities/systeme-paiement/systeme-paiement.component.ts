import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISystemePaiement } from 'app/shared/model/systeme-paiement.model';
import { SystemePaiementService } from './systeme-paiement.service';
import { SystemePaiementDeleteDialogComponent } from './systeme-paiement-delete-dialog.component';

@Component({
  selector: 'jhi-systeme-paiement',
  templateUrl: './systeme-paiement.component.html',
})
export class SystemePaiementComponent implements OnInit, OnDestroy {
  systemePaiements?: ISystemePaiement[];
  eventSubscriber?: Subscription;

  constructor(
    protected systemePaiementService: SystemePaiementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.systemePaiementService.query().subscribe((res: HttpResponse<ISystemePaiement[]>) => (this.systemePaiements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSystemePaiements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISystemePaiement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSystemePaiements(): void {
    this.eventSubscriber = this.eventManager.subscribe('systemePaiementListModification', () => this.loadAll());
  }

  delete(systemePaiement: ISystemePaiement): void {
    const modalRef = this.modalService.open(SystemePaiementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.systemePaiement = systemePaiement;
  }
}
