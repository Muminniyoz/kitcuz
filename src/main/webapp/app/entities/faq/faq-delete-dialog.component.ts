import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFaq } from 'app/shared/model/faq.model';
import { FaqService } from './faq.service';

@Component({
  templateUrl: './faq-delete-dialog.component.html',
})
export class FaqDeleteDialogComponent {
  faq?: IFaq;

  constructor(protected faqService: FaqService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.faqService.delete(id).subscribe(() => {
      this.eventManager.broadcast('faqListModification');
      this.activeModal.close();
    });
  }
}
