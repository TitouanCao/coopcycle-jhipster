import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRestaurant, Restaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';
import { ICooperative } from 'app/shared/model/cooperative.model';
import { CooperativeService } from 'app/entities/cooperative/cooperative.service';

type SelectableEntity = ICompte | ICooperative;

@Component({
  selector: 'jhi-restaurant-update',
  templateUrl: './restaurant-update.component.html',
})
export class RestaurantUpdateComponent implements OnInit {
  isSaving = false;
  owneds: ICompte[] = [];
  cooperatives: ICooperative[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    adress: [null, [Validators.required]],
    owned: [],
    cooperative: [],
  });

  constructor(
    protected restaurantService: RestaurantService,
    protected compteService: CompteService,
    protected cooperativeService: CooperativeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurant }) => {
      this.updateForm(restaurant);

      this.compteService
        .query({ filter: 'owns-is-null' })
        .pipe(
          map((res: HttpResponse<ICompte[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompte[]) => {
          if (!restaurant.owned || !restaurant.owned.id) {
            this.owneds = resBody;
          } else {
            this.compteService
              .find(restaurant.owned.id)
              .pipe(
                map((subRes: HttpResponse<ICompte>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompte[]) => (this.owneds = concatRes));
          }
        });

      this.cooperativeService.query().subscribe((res: HttpResponse<ICooperative[]>) => (this.cooperatives = res.body || []));
    });
  }

  updateForm(restaurant: IRestaurant): void {
    this.editForm.patchValue({
      id: restaurant.id,
      name: restaurant.name,
      adress: restaurant.adress,
      owned: restaurant.owned,
      cooperative: restaurant.cooperative,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurant = this.createFromForm();
    if (restaurant.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurantService.update(restaurant));
    } else {
      this.subscribeToSaveResponse(this.restaurantService.create(restaurant));
    }
  }

  private createFromForm(): IRestaurant {
    return {
      ...new Restaurant(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      adress: this.editForm.get(['adress'])!.value,
      owned: this.editForm.get(['owned'])!.value,
      cooperative: this.editForm.get(['cooperative'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>): void {
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
}
