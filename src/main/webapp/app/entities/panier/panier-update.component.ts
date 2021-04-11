import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPanier, Panier } from 'app/shared/model/panier.model';
import { PanierService } from './panier.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

type SelectableEntity = IProduit | ICompte | IRestaurant;

@Component({
  selector: 'jhi-panier-update',
  templateUrl: './panier-update.component.html',
})
export class PanierUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  comptes: ICompte[] = [];
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    nbElements: [null, [Validators.required]],
    price: [null, [Validators.required]],
    contents: [],
    compte: [],
    restaurant: [],
  });

  constructor(
    protected panierService: PanierService,
    protected produitService: ProduitService,
    protected compteService: CompteService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ panier }) => {
      this.updateForm(panier);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(panier: IPanier): void {
    this.editForm.patchValue({
      id: panier.id,
      nbElements: panier.nbElements,
      price: panier.price,
      contents: panier.contents,
      compte: panier.compte,
      restaurant: panier.restaurant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const panier = this.createFromForm();
    if (panier.id !== undefined) {
      this.subscribeToSaveResponse(this.panierService.update(panier));
    } else {
      this.subscribeToSaveResponse(this.panierService.create(panier));
    }
  }

  private createFromForm(): IPanier {
    return {
      ...new Panier(),
      id: this.editForm.get(['id'])!.value,
      nbElements: this.editForm.get(['nbElements'])!.value,
      price: this.editForm.get(['price'])!.value,
      contents: this.editForm.get(['contents'])!.value,
      compte: this.editForm.get(['compte'])!.value,
      restaurant: this.editForm.get(['restaurant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPanier>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IProduit[], option: IProduit): IProduit {
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
