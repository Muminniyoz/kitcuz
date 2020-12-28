import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { SystemFilesComponent } from './system-files.component';
import { SystemFilesDetailComponent } from './system-files-detail.component';
import { SystemFilesUpdateComponent } from './system-files-update.component';
import { SystemFilesDeleteDialogComponent } from './system-files-delete-dialog.component';
import { systemFilesRoute } from './system-files.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(systemFilesRoute)],
  declarations: [SystemFilesComponent, SystemFilesDetailComponent, SystemFilesUpdateComponent, SystemFilesDeleteDialogComponent],
  entryComponents: [SystemFilesDeleteDialogComponent],
})
export class KitcuzSystemFilesModule {}
