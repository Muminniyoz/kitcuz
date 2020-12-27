import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemFiles } from 'app/shared/model/system-files.model';
import { SystemFilesService } from './system-files.service';

@Component({
  templateUrl: './system-files-delete-dialog.component.html',
})
export class SystemFilesDeleteDialogComponent {
  systemFiles?: ISystemFiles;

  constructor(
    protected systemFilesService: SystemFilesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.systemFilesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('systemFilesListModification');
      this.activeModal.close();
    });
  }
}
