import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KitcuzTestModule } from '../../../test.module';
import { SystemFilesDetailComponent } from 'app/entities/system-files/system-files-detail.component';
import { SystemFiles } from 'app/shared/model/system-files.model';

describe('Component Tests', () => {
  describe('SystemFiles Management Detail Component', () => {
    let comp: SystemFilesDetailComponent;
    let fixture: ComponentFixture<SystemFilesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ systemFiles: new SystemFiles(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [SystemFilesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SystemFilesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemFilesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load systemFiles on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.systemFiles).toEqual(jasmine.objectContaining({ id: 123 }));
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
