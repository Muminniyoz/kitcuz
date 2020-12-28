import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseRequests } from 'app/shared/model/course-requests.model';
import { CourseRequestsService } from './course-requests.service';

@Component({
  templateUrl: './course-requests-delete-dialog.component.html',
})
export class CourseRequestsDeleteDialogComponent {
  courseRequests?: ICourseRequests;

  constructor(
    protected courseRequestsService: CourseRequestsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courseRequestsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courseRequestsListModification');
      this.activeModal.close();
    });
  }
}
