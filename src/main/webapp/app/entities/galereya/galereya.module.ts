import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KitcuzSharedModule } from 'app/shared/shared.module';
import { GalereyaComponent } from './galereya.component';
import { GalereyaDetailComponent } from './galereya-detail.component';
import { GalereyaUpdateComponent } from './galereya-update.component';
import { GalereyaDeleteDialogComponent } from './galereya-delete-dialog.component';
import { galereyaRoute } from './galereya.route';

@NgModule({
  imports: [KitcuzSharedModule, RouterModule.forChild(galereyaRoute)],
  declarations: [GalereyaComponent, GalereyaDetailComponent, GalereyaUpdateComponent, GalereyaDeleteDialogComponent],
  entryComponents: [GalereyaDeleteDialogComponent],
})
export class KitcuzGalereyaModule {}
