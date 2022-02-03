import {NgModule, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SessionExpiredComponent} from './session-expired.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    declarations: [SessionExpiredComponent],
    imports: [
        CommonModule,
        TranslateModule
    ],
    exports: [SessionExpiredComponent]
})
export class SessionExpiredModule {
}
