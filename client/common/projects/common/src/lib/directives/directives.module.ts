import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { TranslateModule } from '@ngx-translate/core';
import { DisableControlDirective } from './disable-control.directive';
import {CustomCurrencyPipe} from './custom-currency.pipe';
import {SortHeaderDirective} from './sort-header.directive';

@NgModule({
    declarations: [
        DisableControlDirective,
        SortHeaderDirective,
        CustomCurrencyPipe
    ],
    imports: [
        CommonModule,
        TranslateModule
    ],
    exports: [
        DisableControlDirective,
        SortHeaderDirective,
        CustomCurrencyPipe
    ],
    providers: []
})
export class DirectivesModule {
}
