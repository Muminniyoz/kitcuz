import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourses } from 'app/shared/model/courses.model';
import { CoursesService } from './courses.service';

@Component({
  templateUrl: './courses-delete-dialog.component.html',
})
export class CoursesDeleteDialogComponent {
  courses?: ICourses;

  constructor(protected coursesService: CoursesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.coursesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('coursesListModification');
      this.activeModal.close();
    });
  }
}
