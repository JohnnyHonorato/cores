import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {RouterModule} from '@angular/router';
import {SidebarComponent} from './sidebar.component';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule,
        RouterModule
    ],
    declarations: [SidebarComponent],
    exports: [SidebarComponent],
    providers: []
})
export class SidebarModule {
}
