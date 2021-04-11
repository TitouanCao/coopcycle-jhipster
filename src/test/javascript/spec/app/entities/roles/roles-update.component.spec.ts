import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyblogTestModule } from '../../../test.module';
import { RolesUpdateComponent } from 'app/entities/roles/roles-update.component';
import { RolesService } from 'app/entities/roles/roles.service';
import { Roles } from 'app/shared/model/roles.model';

describe('Component Tests', () => {
  describe('Roles Management Update Component', () => {
    let comp: RolesUpdateComponent;
    let fixture: ComponentFixture<RolesUpdateComponent>;
    let service: RolesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [RolesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RolesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RolesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Roles(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Roles();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
