import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from './panier.service';
import { PanierDeleteDialogComponent } from './panier-delete-dialog.component';

@Component({
  selector: 'jhi-panier',
  templateUrl: './panier.component.html',
})
export class PanierComponent implements OnInit, OnDestroy {
  paniers?: IPanier[];
  eventSubscriber?: Subscription;

  constructor(protected panierService: PanierService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.panierService.query().subscribe((res: HttpResponse<IPanier[]>) => (this.paniers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaniers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPanier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaniers(): void {
    this.eventSubscriber = this.eventManager.subscribe('panierListModification', () => this.loadAll());
  }

  delete(panier: IPanier): void {
    const modalRef = this.modalService.open(PanierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.panier = panier;
  }
}
