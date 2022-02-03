import {NgModule, ModuleWithProviders} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpClient} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {ToastrModule} from 'ngx-toastr';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';
import {DirectivesModule} from './directives/directives.module';
import {NgxPermissionsModule} from 'ngx-permissions';
import {NotificationService} from './services/notification.service';
import {TranslateService} from './services/translate.service';
import {SearchService} from './services/search.service';
import {SelectSearchService} from './services/select-search.service';
import {CrudService} from './services/crud.service';
import {ModuleConfigService} from './services/module-config.service';
import {LaddaModule} from 'angular2-ladda';
import {NgxMaskModule} from 'ngx-mask';
import {RouterModule} from '@angular/router';
import {EnvironmentService} from './services/environment.service';
import {LoadingComponent} from './components/loading/loading.component';
import {PageSizeComponent} from './components/page-size/page-size.component';
import {ControlMessagesComponent} from './components/control-message/control-message.component';
import {PaginatorComponent} from './components/paginator/paginator.component';
import {SearchListComponent} from './components/search-list/search-list.component';
import {FormButtonsComponent} from './components/form-buttons/form-buttons.component';
import {DataTableComponent} from './components/data-table/data-table.component';
import {DeleteConfirmationComponent} from './components/delete-confirmation/delete-confirmation.component';
import {ContactInfoComponent} from './components/contact-info/contact-info.component';
import {FileService} from './services/file.service';

@NgModule({
    imports: [
        CommonModule,
        DirectivesModule,
        FormsModule,
        NgSelectModule,
        LaddaModule,
        NgxPermissionsModule.forChild(),
        ToastrModule.forRoot(),
        TranslateModule.forChild({
            loader: {
                provide: TranslateLoader,
                useFactory: (http: HttpClient) => new TranslateHttpLoader(http, './assets/i18n/', '.json'),
                deps: [HttpClient]
            }
        }),
        ReactiveFormsModule,
        NgxMaskModule.forRoot(),
        RouterModule
    ],
    providers: [
        CrudService,
        ModuleConfigService,
        NotificationService,
        SearchService,
        SelectSearchService,
        FileService,
        TranslateService
    ],
    declarations: [
        LoadingComponent,
        PageSizeComponent,
        ControlMessagesComponent,
        DeleteConfirmationComponent,
        PaginatorComponent,
        SearchListComponent,
        FormButtonsComponent,
        DataTableComponent,
        ContactInfoComponent
    ],
    exports: [
        LoadingComponent,
        PageSizeComponent,
        ControlMessagesComponent,
        DeleteConfirmationComponent,
        PaginatorComponent,
        SearchListComponent,
        FormButtonsComponent,
        DataTableComponent,
        ContactInfoComponent
    ]
})
export class LibCommonModule {
    public static forRoot(env: any): ModuleWithProviders<LibCommonModule> {
        return {
            ngModule: LibCommonModule,
            providers: [EnvironmentService, {provide: 'env', useValue: env}]
        };
    }
}
