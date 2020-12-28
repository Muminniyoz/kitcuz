import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { StudentGroupDetailComponent } from 'app/entities/student-group/student-group-detail.component';
import { StudentGroup } from 'app/shared/model/student-group.model';

describe('Component Tests', () => {
  describe('StudentGroup Management Detail Component', () => {
    let comp: StudentGroupDetailComponent;
    let fixture: ComponentFixture<StudentGroupDetailComponent>;
    const route = ({ data: of({ studentGroup: new StudentGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [StudentGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StudentGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load studentGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studentGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
