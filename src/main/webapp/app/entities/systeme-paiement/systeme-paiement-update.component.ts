import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISystemePaiement, SystemePaiement } from 'app/shared/model/systeme-paiement.model';
import { SystemePaiementService } from './systeme-paiement.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';

@Component({
  selector: 'jhi-systeme-paiement-update',
  templateUrl: './systeme-paiement-update.component.html',
})
export class SystemePaiementUpdateComponent implements OnInit {
  isSaving = false;
  comptes: ICompte[] = [];

  editForm = this.fb.group({
    id: [],
    method: [null, [Validators.required]],
    agents: [],
  });

  constructor(
    protected systemePaiementService: SystemePaiementService,
    protected compteService: CompteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemePaiement }) => {
      this.updateForm(systemePaiement);

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));
    });
  }

  updateForm(systemePaiement: ISystemePaiement): void {
    this.editForm.patchValue({
      id: systemePaiement.id,
      method: systemePaiement.method,
      agents: systemePaiement.agents,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const systemePaiement = this.createFromForm();
    if (systemePaiement.id !== undefined) {
      this.subscribeToSaveResponse(this.systemePaiementService.update(systemePaiement));
    } else {
      this.subscribeToSaveResponse(this.systemePaiementService.create(systemePaiement));
    }
  }

  private createFromForm(): ISystemePaiement {
    return {
      ...new SystemePaiement(),
      id: this.editForm.get(['id'])!.value,
      method: this.editForm.get(['method'])!.value,
      agents: this.editForm.get(['agents'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemePaiement>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICompte): any {
    return item.id;
  }

  getSelected(selectedVals: ICompte[], option: ICompte): ICompte {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
