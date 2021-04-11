import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyblogTestModule } from '../../../test.module';
import { SystemePaiementDetailComponent } from 'app/entities/systeme-paiement/systeme-paiement-detail.component';
import { SystemePaiement } from 'app/shared/model/systeme-paiement.model';

describe('Component Tests', () => {
  describe('SystemePaiement Management Detail Component', () => {
    let comp: SystemePaiementDetailComponent;
    let fixture: ComponentFixture<SystemePaiementDetailComponent>;
    const route = ({ data: of({ systemePaiement: new SystemePaiement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [SystemePaiementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SystemePaiementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemePaiementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load systemePaiement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.systemePaiement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
