import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFaq, Faq } from 'app/shared/model/faq.model';
import { FaqService } from './faq.service';

@Component({
  selector: 'jhi-faq-update',
  templateUrl: './faq-update.component.html',
})
export class FaqUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    orderNumber: [],
    question: [],
    answer: [],
    active: [],
  });

  constructor(protected faqService: FaqService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ faq }) => {
      this.updateForm(faq);
    });
  }

  updateForm(faq: IFaq): void {
    this.editForm.patchValue({
      id: faq.id,
      orderNumber: faq.orderNumber,
      question: faq.question,
      answer: faq.answer,
      active: faq.active,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const faq = this.createFromForm();
    if (faq.id !== undefined) {
      this.subscribeToSaveResponse(this.faqService.update(faq));
    } else {
      this.subscribeToSaveResponse(this.faqService.create(faq));
    }
  }

  private createFromForm(): IFaq {
    return {
      ...new Faq(),
      id: this.editForm.get(['id'])!.value,
      orderNumber: this.editForm.get(['orderNumber'])!.value,
      question: this.editForm.get(['question'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaq>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
