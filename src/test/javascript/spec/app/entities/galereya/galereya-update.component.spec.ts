import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { GalereyaUpdateComponent } from 'app/entities/galereya/galereya-update.component';
import { GalereyaService } from 'app/entities/galereya/galereya.service';
import { Galereya } from 'app/shared/model/galereya.model';

describe('Component Tests', () => {
  describe('Galereya Management Update Component', () => {
    let comp: GalereyaUpdateComponent;
    let fixture: ComponentFixture<GalereyaUpdateComponent>;
    let service: GalereyaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [GalereyaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GalereyaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GalereyaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GalereyaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Galereya(123);
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
        const entity = new Galereya();
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
