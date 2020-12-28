import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { GalereyaDetailComponent } from 'app/entities/galereya/galereya-detail.component';
import { Galereya } from 'app/shared/model/galereya.model';

describe('Component Tests', () => {
  describe('Galereya Management Detail Component', () => {
    let comp: GalereyaDetailComponent;
    let fixture: ComponentFixture<GalereyaDetailComponent>;
    const route = ({ data: of({ galereya: new Galereya(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [GalereyaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GalereyaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GalereyaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load galereya on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.galereya).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
