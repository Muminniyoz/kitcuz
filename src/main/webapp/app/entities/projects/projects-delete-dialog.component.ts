import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjects } from 'app/shared/model/projects.model';
import { ProjectsService } from './projects.service';

@Component({
  templateUrl: './projects-delete-dialog.component.html',
})
export class ProjectsDeleteDialogComponent {
  projects?: IProjects;

  constructor(protected projectsService: ProjectsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectsListModification');
      this.activeModal.close();
    });
  }
}
