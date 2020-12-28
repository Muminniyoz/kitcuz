import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGalereyImages } from 'app/shared/model/galerey-images.model';
import { GalereyImagesService } from './galerey-images.service';

@Component({
  templateUrl: './galerey-images-delete-dialog.component.html',
})
export class GalereyImagesDeleteDialogComponent {
  galereyImages?: IGalereyImages;

  constructor(
    protected galereyImagesService: GalereyImagesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galereyImagesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('galereyImagesListModification');
      this.activeModal.close();
    });
  }
}
