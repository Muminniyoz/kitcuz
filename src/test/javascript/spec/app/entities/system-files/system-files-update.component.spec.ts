import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { SystemFilesUpdateComponent } from 'app/entities/system-files/system-files-update.component';
import { SystemFilesService } from 'app/entities/system-files/system-files.service';
import { SystemFiles } from 'app/shared/model/system-files.model';

describe('Component Tests', () => {
  describe('SystemFiles Management Update Component', () => {
    let comp: SystemFilesUpdateComponent;
    let fixture: ComponentFixture<SystemFilesUpdateComponent>;
    let service: SystemFilesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [SystemFilesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SystemFilesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemFilesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemFilesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SystemFiles(123);
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
        const entity = new SystemFiles();
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
