import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LibCommonModule} from 'common';
import {TranslateModule} from '@ngx-translate/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ColorPickerModule} from 'ngx-color-picker';
import { TrackerService } from 'src/app/shared/services/tracker.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPermissionsModule } from 'ngx-permissions';
import {PopoverTagsComponent} from './popover-tags.component';
import {PopoverTagsListComponent} from './popover-tags-list/popover-tags-list.component';
import {PopoverTagsEditComponent} from './popover-tags-edit/popover-tags-edit.component';
import {PopoverTagsRemoveComponent} from './popover-tags-remove/popover-tags-remove.component';
import {TagService} from '@shared/services/tag.service';
@NgModule({
    imports: [
        CommonModule,
        LibCommonModule,
        TranslateModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule,
        ColorPickerModule,
        NgxPermissionsModule
    ],
    declarations: [
        PopoverTagsComponent,
        PopoverTagsListComponent,
        PopoverTagsEditComponent,
        PopoverTagsRemoveComponent
    ],
    exports: [
        PopoverTagsComponent
    ],
    providers: [
        TrackerService,
        TagService
    ]
})
export class PopoverTagsModule {
}
