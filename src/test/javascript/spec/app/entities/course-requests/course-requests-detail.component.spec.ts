import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { CourseRequestsDetailComponent } from 'app/entities/course-requests/course-requests-detail.component';
import { CourseRequests } from 'app/shared/model/course-requests.model';

describe('Component Tests', () => {
  describe('CourseRequests Management Detail Component', () => {
    let comp: CourseRequestsDetailComponent;
    let fixture: ComponentFixture<CourseRequestsDetailComponent>;
    const route = ({ data: of({ courseRequests: new CourseRequests(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [CourseRequestsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CourseRequestsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourseRequestsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load courseRequests on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.courseRequests).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
