import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { PlanningDetailComponent } from 'app/entities/planning/planning-detail.component';
import { Planning } from 'app/shared/model/planning.model';

describe('Component Tests', () => {
  describe('Planning Management Detail Component', () => {
    let comp: PlanningDetailComponent;
    let fixture: ComponentFixture<PlanningDetailComponent>;
    const route = ({ data: of({ planning: new Planning(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [PlanningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanningDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planning on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planning).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
