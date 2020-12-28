import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { CoursesUpdateComponent } from 'app/entities/courses/courses-update.component';
import { CoursesService } from 'app/entities/courses/courses.service';
import { Courses } from 'app/shared/model/courses.model';

describe('Component Tests', () => {
  describe('Courses Management Update Component', () => {
    let comp: CoursesUpdateComponent;
    let fixture: ComponentFixture<CoursesUpdateComponent>;
    let service: CoursesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [CoursesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CoursesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoursesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoursesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Courses(123);
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
        const entity = new Courses();
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
