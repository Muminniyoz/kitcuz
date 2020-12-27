import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { StudentGroupComponent } from './student-group.component';
import { StudentGroupDetailComponent } from './student-group-detail.component';
import { StudentGroupUpdateComponent } from './student-group-update.component';
import { StudentGroupDeleteDialogComponent } from './student-group-delete-dialog.component';
import { studentGroupRoute } from './student-group.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(studentGroupRoute)],
  declarations: [StudentGroupComponent, StudentGroupDetailComponent, StudentGroupUpdateComponent, StudentGroupDeleteDialogComponent],
  entryComponents: [StudentGroupDeleteDialogComponent],
})
export class KitcuzStudentGroupModule {}
