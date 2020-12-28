import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFaq } from 'app/shared/model/faq.model';

@Component({
  selector: 'jhi-faq-detail',
  templateUrl: './faq-detail.component.html',
})
export class FaqDetailComponent implements OnInit {
  faq: IFaq | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ faq }) => (this.faq = faq));
  }

  previousState(): void {
    window.history.back();
  }
}
