import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyblogTestModule } from '../../../test.module';
import { SystemePaiementComponent } from 'app/entities/systeme-paiement/systeme-paiement.component';
import { SystemePaiementService } from 'app/entities/systeme-paiement/systeme-paiement.service';
import { SystemePaiement } from 'app/shared/model/systeme-paiement.model';

describe('Component Tests', () => {
  describe('SystemePaiement Management Component', () => {
    let comp: SystemePaiementComponent;
    let fixture: ComponentFixture<SystemePaiementComponent>;
    let service: SystemePaiementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [SystemePaiementComponent],
      })
        .overrideTemplate(SystemePaiementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemePaiementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemePaiementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SystemePaiement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.systemePaiements && comp.systemePaiements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
