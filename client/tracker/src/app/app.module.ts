import {BrowserModule} from '@angular/platform-browser';
import {Injector, LOCALE_ID, NgModule} from '@angular/core';
import {NgxPermissionsModule} from 'ngx-permissions';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LibCommonModule, setAppInjector} from 'common';
import {environment} from '@environments/environment';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OAuthModule} from 'angular-oauth2-oidc';
import {RouterModule} from '@angular/router';
import {ToastrModule} from 'ngx-toastr';
import {EmptyRouteComponent} from './components/empty-route/empty-route.component';
import {BoardEditComponent} from './components/board-edit/board-edit.component';
import {FirstStepComponent} from './components/board-edit/first-step/first-step.component';
import {ArchwizardModule} from 'angular-archwizard';
import {SecondStepDataComponent} from './components/board-edit/second-step/second-step-data.component';
import {AttachmentTypesComponent} from './components/board-edit/second-step/attachment-types/attachment-types.component';
import {ResizeAndReorderComponent} from './components/board-edit/second-step/resize-and-reorder/resize-and-reorder.component';
import {CustomAttGridComponent} from './components/board-edit/second-step/resize-and-reorder/custom-att-grid/custom-att-grid.component';
import {CommonModule, registerLocaleData} from '@angular/common';
import {NgSelectModule} from '@ng-select/ng-select';
import {GridsterModule} from 'angular-gridster2';
import {CustomAttEditComponent} from './components/board-edit/custom-att-edit/custom-att-edit.component';
import {ModalConfirmationMonetaryComponent} from './components/board-edit/custom-att-edit/modal-confirmation-monetary/modal-confirmation-monetary.component';
import {NgxMaskModule} from 'ngx-mask';
import {TagInputModule} from 'ngx-chips';
import {LaddaModule} from 'angular2-ladda';
import {BoardListComponent} from './components/board-list/board-list.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {CustomTranslateLoaderService} from '@shared/services/custom-translate-loader.service';
import {TrackerService} from '@shared/services/tracker.service';
import {CurrencyService} from '@shared/services/currency.service';
import {FormattingDecimalFieldDirective} from './components/board-edit/custom-att-edit/formatting-decimal-field.directive';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {APP_DATE_FORMATS, AppDateAdapter} from '@shared/util/date-adpater';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {ThirdStepComponent} from './components/board-edit/third-step/third-step.component';
import {ModalToAddStatusComponent} from './components/board-edit/third-step/modal-to-add-status/modal-to-add-status.component';
import {DragDropStatusChangeComponent} from './components/board-edit/third-step/drag-drop-status-change/drag-drop-status-change.component';
import {DragDropItemsComponent} from './components/board-edit/drag-drop-items/drag-drop-items.component';
import {ModalDeleteRelationshipComponent} from './components/board-edit/drag-drop-items/modal-delete-relationship/modal-delete-relationship.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {ModalSettingStatusAttributesComponent} from './components/board-edit/third-step/modal-setting-status-attributes/modal-setting-status-attributes.component';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BoardViewComponent} from './components/board-view/board-view.component';
import {DragulaModule, DragulaService} from 'ng2-dragula';
import {FourthStepComponent} from './components/board-edit/fourth-step/fourth-step.component';
import {CustomizableAttributesComponent} from './components/customizable-attributes/customizable-attributes.component';
import {CurrencyMaskModule} from 'ng2-currency-mask';
import {UploadMultipleFilesComponent} from './components/upload-multiple-files/upload-multiple-files.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSidenavModule} from '@angular/material/sidenav';
import {StatusColumnComponent} from './components/board-view/status-column/status-column.component';
import {TrackerEditComponent} from './components/board-view/tracker-edit/tracker-edit.component';
import {DragDropDirective} from './components/upload-multiple-files/drag-drop.directive';
import {PopoverTagsModule} from './components/board-view/tracker-edit/popover-tags/popover-tags.module';
import {TrackerViewComponent} from './components/board-view/tracker-view/tracker-view.component';
import {CustomizableAttributesViewComponent} from './components/board-view/tracker-view/customizable-attributes-view/customizable-attributes-view.component';
import {CommentComponent} from './components/board-view/tracker-view/comment/comment.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {PopoverChecklistComponent} from './components/board-view/checklist/popover-checklist/popover-checklist.component';
import {ChecklistComponent} from './components/board-view/checklist/checklist.component';
import {ChecklistItemComponent} from './components/board-view/checklist/checklist-item/checklist-item.component';
import {IndexStorageService} from '@shared/services/index-storage.service';
import {DraftModalComponent} from './components/board-view/tracker-edit/draft-modal/draft-modal.component';
import {CardComponent} from './components/board-view/card/card.component';
import localePt from '@angular/common/locales/pt';
import {ViewCustomizableAttributesComponent} from './components/board-view/card/view-customizable-attributes/view-customizable-attributes.component';
import {TempService} from '@shared/services/temp.service';
import {AssigneesComponent} from './components/board-view/card/assignees/assignees.component';
import {QuickViewCommentsComponent} from './components/board-view/card/quick-view-comments/quick-view-comments.component';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {QuickViewAttachmentComponent} from './components/board-view/card/quick-view-attachment/quick-view-attachment.component';
import {TrackerFileService} from '@shared/services/tracker-file-service.service';
import {FilterEditComponent} from './components/board-view/filters/filter-edit/filter-edit.component';
import {FilterService} from '@shared/services/filter.service';
import {OptionsFilterComponent} from './components/board-view/filters/options-filter/options-filter.component';
import {FiltersListComponent} from './components/board-view/filters/filters-list/filters-list.component';
import {FiltersComponent} from './components/board-view/filters/filters.component';
import {FavoriteIconPipe} from './components/board-view/filters/filters-list/favorite-icon-pipe';

registerLocaleData(localePt);

@NgModule({
    declarations: [
        AppComponent,
        EmptyRouteComponent,
        BoardListComponent,
        BoardEditComponent,
        FirstStepComponent,
        SecondStepDataComponent,
        AttachmentTypesComponent,
        ResizeAndReorderComponent,
        CustomAttGridComponent,
        CustomAttEditComponent,
        ModalConfirmationMonetaryComponent,
        FormattingDecimalFieldDirective,
        ThirdStepComponent,
        ModalToAddStatusComponent,
        DragDropStatusChangeComponent,
        DragDropItemsComponent,
        ModalDeleteRelationshipComponent,
        ModalSettingStatusAttributesComponent,
        BoardViewComponent,
        FourthStepComponent,
        PopoverChecklistComponent,
        CustomizableAttributesComponent,
        UploadMultipleFilesComponent,
        StatusColumnComponent,
        TrackerEditComponent,
        DragDropDirective,
        TrackerViewComponent,
        CustomizableAttributesViewComponent,
        CommentComponent,
        ChecklistComponent,
        ChecklistItemComponent,
        DraftModalComponent,
        CardComponent,
        AssigneesComponent,
        ViewCustomizableAttributesComponent,
        QuickViewCommentsComponent,
        QuickViewAttachmentComponent,
        FilterEditComponent,
        OptionsFilterComponent,
        FiltersListComponent,
        FiltersComponent,
        FavoriteIconPipe
    ],
    imports: [
        CommonModule,
        HttpClientModule,
        MatSidenavModule,
        NgSelectModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        DragulaModule.forRoot(),
        ModalModule.forRoot(),
        LibCommonModule.forRoot(environment),
        ModalModule.forRoot(),
        NgxPermissionsModule.forRoot(),
        OAuthModule.forRoot(),
        ModalModule.forRoot(),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useClass: CustomTranslateLoaderService,
                deps: [HttpClient]
            }
        }),
        RouterModule,
        ToastrModule.forRoot(),
        ArchwizardModule,
        GridsterModule,
        NgxMaskModule,
        TagInputModule,
        LaddaModule,
        NgSelectModule,
        MatDatepickerModule,
        CurrencyMaskModule,
        DragDropModule,
        MatSlideToggleModule,
        PopoverTagsModule,
        InfiniteScrollModule,
        MatProgressBarModule,
        NgbModule,
        MatCheckboxModule
    ],
    providers: [
        DragulaService,
        CustomTranslateLoaderService,
        CurrencyService,
        TrackerService,
        TempService,
        IndexStorageService,
        TrackerFileService,
        FilterService,
        {provide: DateAdapter, useClass: AppDateAdapter},
        {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS},
        {provide: MAT_DATE_LOCALE, useValue: 'pt-BR'},
        {provide: LOCALE_ID, useValue: 'pt-BR'}
    ],
    exports: [
        FormattingDecimalFieldDirective
    ],

    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(private injector: Injector) {
        setAppInjector(this.injector);
    }
}
