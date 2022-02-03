import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, Injector, NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RouteReuseStrategy, RouterModule} from '@angular/router';
import {MicroFrontendRouteReuseStrategy} from 'src/app/shared/spa-host/services/route-reuse-strategy';
import {FlexModule} from '@angular/flex-layout';
import {SSOService} from '@shared/service/sso.service';
import {LibCommonModule, setAppInjector} from 'common';
import {environment} from '@environments/environment';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {OAuthModule} from 'angular-oauth2-oidc';
import {NgxPermissionsModule} from 'ngx-permissions';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {ToastrModule} from 'ngx-toastr';
import {HeaderModule} from '@shared/components/header/header.module';
import {SidebarComponent} from '@shared/components/sidebar/sidebar.component';
import {AccountService} from '@shared/service/account.service';
import {FooterComponent} from '@shared/components/footer/footer.component';
import {DashboardModule} from '@components/dashboard/dashboard.module';
import {OrganizationModule} from '@components/organization/organization.module';
import {BusinessOpportunityModule} from '@components/business-opportunity/business-opportunity.module';
import {TempService} from '@shared/service/temp.service';
import {GlobalErrorHandler} from '@shared/util/global-error-handler';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BreadcrumbComponent} from '@shared/components/breadcrumb/breadcrumb.component';
import {IndexStorageService} from '@shared/service/index-storage.service';

@NgModule({
    declarations: [
        AppComponent,
        SidebarComponent,
        FooterComponent,
        BreadcrumbComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        OAuthModule.forRoot(),
        FlexModule,
        LibCommonModule.forRoot(environment),
        NgxPermissionsModule.forRoot(),
        ModalModule.forRoot(),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (http: HttpClient) => new TranslateHttpLoader(http, '../assets/i18n/', '.json'),
                deps: [HttpClient]
            }
        }),
        RouterModule,
        ToastrModule.forRoot(),
        HeaderModule,
        DashboardModule,
        OrganizationModule,
        BusinessOpportunityModule
    ],
    providers: [
        {provide: RouteReuseStrategy, useClass: MicroFrontendRouteReuseStrategy},
        // {provide: ErrorHandler, useClass: GlobalErrorHandler},
        SSOService,
        AccountService,
        TempService,
        IndexStorageService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(private injector: Injector) {
        setAppInjector(this.injector);
    }
}
