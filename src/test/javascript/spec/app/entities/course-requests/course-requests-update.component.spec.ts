import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { CourseRequestsUpdateComponent } from 'app/entities/course-requests/course-requests-update.component';
import { CourseRequestsService } from 'app/entities/course-requests/course-requests.service';
import { CourseRequests } from 'app/shared/model/course-requests.model';

describe('Component Tests', () => {
  describe('CourseRequests Management Update Component', () => {
    let comp: CourseRequestsUpdateComponent;
    let fixture: ComponentFixture<CourseRequestsUpdateComponent>;
    let service: CourseRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [CourseRequestsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CourseRequestsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CourseRequestsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourseRequestsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CourseRequests(123);
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
        const entity = new CourseRequests();
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
