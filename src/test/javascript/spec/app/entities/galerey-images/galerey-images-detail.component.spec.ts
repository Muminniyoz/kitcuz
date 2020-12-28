import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { GalereyImagesDetailComponent } from 'app/entities/galerey-images/galerey-images-detail.component';
import { GalereyImages } from 'app/shared/model/galerey-images.model';

describe('Component Tests', () => {
  describe('GalereyImages Management Detail Component', () => {
    let comp: GalereyImagesDetailComponent;
    let fixture: ComponentFixture<GalereyImagesDetailComponent>;
    const route = ({ data: of({ galereyImages: new GalereyImages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [GalereyImagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GalereyImagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GalereyImagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load galereyImages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.galereyImages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
