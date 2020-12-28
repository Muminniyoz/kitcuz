import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { GalereyImagesComponent } from './galerey-images.component';
import { GalereyImagesDetailComponent } from './galerey-images-detail.component';
import { GalereyImagesUpdateComponent } from './galerey-images-update.component';
import { GalereyImagesDeleteDialogComponent } from './galerey-images-delete-dialog.component';
import { galereyImagesRoute } from './galerey-images.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(galereyImagesRoute)],
  declarations: [GalereyImagesComponent, GalereyImagesDetailComponent, GalereyImagesUpdateComponent, GalereyImagesDeleteDialogComponent],
  entryComponents: [GalereyImagesDeleteDialogComponent],
})
export class KitcuzGalereyImagesModule {}
