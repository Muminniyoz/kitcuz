import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { CourseGroupComponent } from './course-group.component';
import { CourseGroupDetailComponent } from './course-group-detail.component';
import { CourseGroupUpdateComponent } from './course-group-update.component';
import { CourseGroupDeleteDialogComponent } from './course-group-delete-dialog.component';
import { courseGroupRoute } from './course-group.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(courseGroupRoute)],
  declarations: [CourseGroupComponent, CourseGroupDetailComponent, CourseGroupUpdateComponent, CourseGroupDeleteDialogComponent],
  entryComponents: [CourseGroupDeleteDialogComponent],
})
export class KitcuzCourseGroupModule {}
