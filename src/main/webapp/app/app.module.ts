import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { KitcuzSharedModule } from 'app/shared/shared.module';
import { KitcuzCoreModule } from 'app/core/core.module';
import { KitcuzAppRoutingModule } from './app-routing.module';
import { KitcuzHomeModule } from './home/home.module';
import { KitcuzEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    KitcuzSharedModule,
    KitcuzCoreModule,
    KitcuzHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    KitcuzEntityModule,
    KitcuzAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class KitcuzAppModule {}
