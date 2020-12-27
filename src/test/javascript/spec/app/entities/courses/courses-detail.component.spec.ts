import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KitcuzTestModule } from '../../../test.module';
import { CoursesDetailComponent } from 'app/entities/courses/courses-detail.component';
import { Courses } from 'app/shared/model/courses.model';

describe('Component Tests', () => {
  describe('Courses Management Detail Component', () => {
    let comp: CoursesDetailComponent;
    let fixture: ComponentFixture<CoursesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ courses: new Courses(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [CoursesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CoursesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoursesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load courses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.courses).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
