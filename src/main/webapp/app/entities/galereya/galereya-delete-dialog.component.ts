import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGalereya } from 'app/shared/model/galereya.model';
import { GalereyaService } from './galereya.service';

@Component({
  templateUrl: './galereya-delete-dialog.component.html',
})
export class GalereyaDeleteDialogComponent {
  galereya?: IGalereya;

  constructor(protected galereyaService: GalereyaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galereyaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('galereyaListModification');
      this.activeModal.close();
    });
  }
}
