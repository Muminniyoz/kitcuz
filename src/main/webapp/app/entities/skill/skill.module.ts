import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { SkillComponent } from './skill.component';
import { SkillDetailComponent } from './skill-detail.component';
import { SkillUpdateComponent } from './skill-update.component';
import { SkillDeleteDialogComponent } from './skill-delete-dialog.component';
import { skillRoute } from './skill.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(skillRoute)],
  declarations: [SkillComponent, SkillDetailComponent, SkillUpdateComponent, SkillDeleteDialogComponent],
  entryComponents: [SkillDeleteDialogComponent],
})
export class KitcuzSkillModule {}
