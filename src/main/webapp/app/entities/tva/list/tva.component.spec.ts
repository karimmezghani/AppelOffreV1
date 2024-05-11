import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TvaService } from '../service/tva.service';

import { TvaComponent } from './tva.component';

describe('Component Tests', () => {
  describe('Tva Management Component', () => {
    let comp: TvaComponent;
    let fixture: ComponentFixture<TvaComponent>;
    let service: TvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TvaComponent],
      })
        .overrideTemplate(TvaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TvaComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TvaService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tvas?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
