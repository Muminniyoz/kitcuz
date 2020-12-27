import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { AbilityStudentComponent } from './ability-student.component';
import { AbilityStudentDetailComponent } from './ability-student-detail.component';
import { AbilityStudentUpdateComponent } from './ability-student-update.component';
import { AbilityStudentDeleteDialogComponent } from './ability-student-delete-dialog.component';
import { abilityStudentRoute } from './ability-student.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(abilityStudentRoute)],
  declarations: [
    AbilityStudentComponent,
    AbilityStudentDetailComponent,
    AbilityStudentUpdateComponent,
    AbilityStudentDeleteDialogComponent,
  ],
  entryComponents: [AbilityStudentDeleteDialogComponent],
})
export class KitcuzAbilityStudentModule {}
