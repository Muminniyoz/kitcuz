import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { AbilityStudentUpdateComponent } from 'app/entities/ability-student/ability-student-update.component';
import { AbilityStudentService } from 'app/entities/ability-student/ability-student.service';
import { AbilityStudent } from 'app/shared/model/ability-student.model';

describe('Component Tests', () => {
  describe('AbilityStudent Management Update Component', () => {
    let comp: AbilityStudentUpdateComponent;
    let fixture: ComponentFixture<AbilityStudentUpdateComponent>;
    let service: AbilityStudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [AbilityStudentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AbilityStudentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AbilityStudentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AbilityStudentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AbilityStudent(123);
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
        const entity = new AbilityStudent();
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
