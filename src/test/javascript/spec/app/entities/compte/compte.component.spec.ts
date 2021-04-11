import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyblogTestModule } from '../../../test.module';
import { CompteComponent } from 'app/entities/compte/compte.component';
import { CompteService } from 'app/entities/compte/compte.service';
import { Compte } from 'app/shared/model/compte.model';

describe('Component Tests', () => {
  describe('Compte Management Component', () => {
    let comp: CompteComponent;
    let fixture: ComponentFixture<CompteComponent>;
    let service: CompteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyblogTestModule],
        declarations: [CompteComponent],
      })
        .overrideTemplate(CompteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Compte(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comptes && comp.comptes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
