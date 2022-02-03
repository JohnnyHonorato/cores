import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {TranslateModule} from '@ngx-translate/core';
import {LibCommonModule} from 'common';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    LibCommonModule
  ],
  declarations: [
    DashboardComponent
  ]
})
export class DashboardModule {
}
