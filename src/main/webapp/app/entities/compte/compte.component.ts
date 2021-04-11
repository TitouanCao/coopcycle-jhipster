import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from './compte.service';
import { CompteDeleteDialogComponent } from './compte-delete-dialog.component';

@Component({
  selector: 'jhi-compte',
  templateUrl: './compte.component.html',
})
export class CompteComponent implements OnInit, OnDestroy {
  comptes?: ICompte[];
  eventSubscriber?: Subscription;

  constructor(protected compteService: CompteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComptes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompte): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComptes(): void {
    this.eventSubscriber = this.eventManager.subscribe('compteListModification', () => this.loadAll());
  }

  delete(compte: ICompte): void {
    const modalRef = this.modalService.open(CompteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compte = compte;
  }
}
