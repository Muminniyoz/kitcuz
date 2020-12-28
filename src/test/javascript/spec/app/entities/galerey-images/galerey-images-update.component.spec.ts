import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { GalereyImagesUpdateComponent } from 'app/entities/galerey-images/galerey-images-update.component';
import { GalereyImagesService } from 'app/entities/galerey-images/galerey-images.service';
import { GalereyImages } from 'app/shared/model/galerey-images.model';

describe('Component Tests', () => {
  describe('GalereyImages Management Update Component', () => {
    let comp: GalereyImagesUpdateComponent;
    let fixture: ComponentFixture<GalereyImagesUpdateComponent>;
    let service: GalereyImagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [GalereyImagesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GalereyImagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GalereyImagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GalereyImagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GalereyImages(123);
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
        const entity = new GalereyImages();
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
