import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KitcuzTestModule } from '../../../test.module';
import { FaqDetailComponent } from 'app/entities/faq/faq-detail.component';
import { Faq } from 'app/shared/model/faq.model';

describe('Component Tests', () => {
  describe('Faq Management Detail Component', () => {
    let comp: FaqDetailComponent;
    let fixture: ComponentFixture<FaqDetailComponent>;
    const route = ({ data: of({ faq: new Faq(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KitcuzTestModule],
        declarations: [FaqDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FaqDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FaqDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load faq on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.faq).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
