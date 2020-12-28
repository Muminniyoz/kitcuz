import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { StudentGroupUpdateComponent } from 'app/entities/student-group/student-group-update.component';
import { StudentGroupService } from 'app/entities/student-group/student-group.service';
import { StudentGroup } from 'app/shared/model/student-group.model';

describe('Component Tests', () => {
  describe('StudentGroup Management Update Component', () => {
    let comp: StudentGroupUpdateComponent;
    let fixture: ComponentFixture<StudentGroupUpdateComponent>;
    let service: StudentGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [StudentGroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StudentGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentGroup(123);
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
        const entity = new StudentGroup();
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
