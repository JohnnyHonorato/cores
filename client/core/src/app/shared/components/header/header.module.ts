import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from './header.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {TranslateModule} from '@ngx-translate/core';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FlexLayoutModule,
    TranslateModule
  ],
  declarations: [HeaderComponent],
  exports: [HeaderComponent],
  providers: []
})
export class HeaderModule {
}
