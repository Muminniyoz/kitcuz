import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { ProjectsComponent } from './projects.component';
import { ProjectsDetailComponent } from './projects-detail.component';
import { ProjectsUpdateComponent } from './projects-update.component';
import { ProjectsDeleteDialogComponent } from './projects-delete-dialog.component';
import { projectsRoute } from './projects.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(projectsRoute)],
  declarations: [ProjectsComponent, ProjectsDetailComponent, ProjectsUpdateComponent, ProjectsDeleteDialogComponent],
  entryComponents: [ProjectsDeleteDialogComponent],
})
export class KitcuzProjectsModule {}
