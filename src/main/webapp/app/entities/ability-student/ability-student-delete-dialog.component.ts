import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbilityStudent } from 'app/shared/model/ability-student.model';
import { AbilityStudentService } from './ability-student.service';

@Component({
  templateUrl: './ability-student-delete-dialog.component.html',
})
export class AbilityStudentDeleteDialogComponent {
  abilityStudent?: IAbilityStudent;

  constructor(
    protected abilityStudentService: AbilityStudentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.abilityStudentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('abilityStudentListModification');
      this.activeModal.close();
    });
  }
}
