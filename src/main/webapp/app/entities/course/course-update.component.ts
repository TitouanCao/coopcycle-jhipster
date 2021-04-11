import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from 'app/entities/panier/panier.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

type SelectableEntity = IPanier | ICompte | IRestaurant;

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html',
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;
  orders: IPanier[] = [];
  comptes: ICompte[] = [];
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    timeRequired: [null, [Validators.required]],
    order: [],
    agents: [],
    restaurant: [],
  });

  constructor(
    protected courseService: CourseService,
    protected panierService: PanierService,
    protected compteService: CompteService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);

      this.panierService
        .query({ filter: 'course-is-null' })
        .pipe(
          map((res: HttpResponse<IPanier[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPanier[]) => {
          if (!course.order || !course.order.id) {
            this.orders = resBody;
          } else {
            this.panierService
              .find(course.order.id)
              .pipe(
                map((subRes: HttpResponse<IPanier>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPanier[]) => (this.orders = concatRes));
          }
        });

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      timeRequired: course.timeRequired,
      order: course.order,
      agents: course.agents,
      restaurant: course.restaurant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      timeRequired: this.editForm.get(['timeRequired'])!.value,
      order: this.editForm.get(['order'])!.value,
      agents: this.editForm.get(['agents'])!.value,
      restaurant: this.editForm.get(['restaurant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
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
