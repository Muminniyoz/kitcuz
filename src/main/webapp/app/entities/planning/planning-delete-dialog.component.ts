import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanning } from 'app/shared/model/planning.model';
import { PlanningService } from './planning.service';

@Component({
  templateUrl: './planning-delete-dialog.component.html',
})
export class PlanningDeleteDialogComponent {
  planning?: IPlanning;

  constructor(protected planningService: PlanningService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planningService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planningListModification');
      this.activeModal.close();
    });
  }
}
