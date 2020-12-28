import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { FaqComponent } from './faq.component';
import { FaqDetailComponent } from './faq-detail.component';
import { FaqUpdateComponent } from './faq-update.component';
import { FaqDeleteDialogComponent } from './faq-delete-dialog.component';
import { faqRoute } from './faq.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(faqRoute)],
  declarations: [FaqComponent, FaqDetailComponent, FaqUpdateComponent, FaqDeleteDialogComponent],
  entryComponents: [FaqDeleteDialogComponent],
})
export class KitcuzFaqModule {}
