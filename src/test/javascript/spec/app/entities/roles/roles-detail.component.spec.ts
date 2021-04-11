import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyblogTestModule } from '../../../test.module';
import { RolesDetailComponent } from 'app/entities/roles/roles-detail.component';
import { Roles } from 'app/shared/model/roles.model';

describe('Component Tests', () => {
  describe('Roles Management Detail Component', () => {
    let comp: RolesDetailComponent;
    let fixture: ComponentFixture<RolesDetailComponent>;
    const route = ({ data: of({ roles: new Roles(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [RolesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RolesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RolesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load roles on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.roles).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
