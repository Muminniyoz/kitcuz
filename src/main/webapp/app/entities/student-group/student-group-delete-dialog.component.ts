import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentGroup } from 'app/shared/model/student-group.model';
import { StudentGroupService } from './student-group.service';

@Component({
  templateUrl: './student-group-delete-dialog.component.html',
})
export class StudentGroupDeleteDialogComponent {
  studentGroup?: IStudentGroup;

  constructor(
    protected studentGroupService: StudentGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studentGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studentGroupListModification');
      this.activeModal.close();
    });
  }
}
