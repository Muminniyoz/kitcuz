import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseGroup } from 'app/shared/model/course-group.model';
import { CourseGroupService } from './course-group.service';

@Component({
  templateUrl: './course-group-delete-dialog.component.html',
})
export class CourseGroupDeleteDialogComponent {
  courseGroup?: ICourseGroup;

  constructor(
    protected courseGroupService: CourseGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courseGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courseGroupListModification');
      this.activeModal.close();
    });
  }
}
