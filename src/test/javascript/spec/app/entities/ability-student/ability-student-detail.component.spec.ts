import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KitcuzTestModule } from '../../../test.module';
import { AbilityStudentDetailComponent } from 'app/entities/ability-student/ability-student-detail.component';
import { AbilityStudent } from 'app/shared/model/ability-student.model';

describe('Component Tests', () => {
  describe('AbilityStudent Management Detail Component', () => {
    let comp: AbilityStudentDetailComponent;
    let fixture: ComponentFixture<AbilityStudentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ abilityStudent: new AbilityStudent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [AbilityStudentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AbilityStudentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AbilityStudentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load abilityStudent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.abilityStudent).toEqual(jasmine.objectContaining({ id: 123 }));
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
