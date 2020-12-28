import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { CourseRequestsComponent } from './course-requests.component';
import { CourseRequestsDetailComponent } from './course-requests-detail.component';
import { CourseRequestsUpdateComponent } from './course-requests-update.component';
import { CourseRequestsDeleteDialogComponent } from './course-requests-delete-dialog.component';
import { courseRequestsRoute } from './course-requests.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(courseRequestsRoute)],
  declarations: [
    CourseRequestsComponent,
    CourseRequestsDetailComponent,
    CourseRequestsUpdateComponent,
    CourseRequestsDeleteDialogComponent,
  ],
  entryComponents: [CourseRequestsDeleteDialogComponent],
})
export class KitcuzCourseRequestsModule {}
