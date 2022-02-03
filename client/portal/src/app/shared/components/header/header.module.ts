import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

import {TranslateModule} from '@ngx-translate/core';
import {HeaderComponent} from './header.component';
import {FlexLayoutModule} from '@angular/flex-layout';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule,
        RouterModule,
        FlexLayoutModule
    ],
    declarations: [HeaderComponent],
    exports: [HeaderComponent],
    providers: []
})
export class HeaderModule {
}
