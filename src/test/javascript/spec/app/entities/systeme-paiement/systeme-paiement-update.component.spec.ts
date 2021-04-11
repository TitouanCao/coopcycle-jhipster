import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyblogTestModule } from '../../../test.module';
import { SystemePaiementUpdateComponent } from 'app/entities/systeme-paiement/systeme-paiement-update.component';
import { SystemePaiementService } from 'app/entities/systeme-paiement/systeme-paiement.service';
import { SystemePaiement } from 'app/shared/model/systeme-paiement.model';

describe('Component Tests', () => {
  describe('SystemePaiement Management Update Component', () => {
    let comp: SystemePaiementUpdateComponent;
    let fixture: ComponentFixture<SystemePaiementUpdateComponent>;
    let service: SystemePaiementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [SystemePaiementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SystemePaiementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemePaiementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemePaiementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SystemePaiement(123);
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
        const entity = new SystemePaiement();
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
