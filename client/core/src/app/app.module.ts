import {BrowserModule} from '@angular/platform-browser';
import {Injector, NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderModule} from '@shared/components/header/header.module';
import {SSOService} from '@shared/services/sso.service';
import {OAuthModule} from 'angular-oauth2-oidc';
import {AccountService} from '@shared/services/account.service';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {SidebarComponent} from '@shared/components/sidebar/sidebar.component';
import {DashboardModule} from '@components/dashboard/dashboard.module';
import {FlexModule} from '@angular/flex-layout';
import {NgxPermissionsModule} from 'ngx-permissions';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {LibCommonModule, setAppInjector} from 'common';
import {RouterModule} from '@angular/router';
import {LaddaModule} from 'angular2-ladda';
import {environment} from '@environments/environment';
import {BreadcrumbComponent} from '@shared/components/breadcrumb/breadcrumb.component';
import TokenInterceptor from './shared/util/token.interceptor';
import {SessionExpiredModule} from '@shared/components/session-expired/session-expired.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {FooterComponent} from '@shared/components/footer/footer.component';

@NgModule({
    declarations: [
        AppComponent,
        FooterComponent,
        SidebarComponent,
        BreadcrumbComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        HeaderModule,
        HttpClientModule,
        OAuthModule.forRoot(),
        DashboardModule,
        FlexModule,
        NgxPermissionsModule.forRoot(),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (http: HttpClient) => new TranslateHttpLoader(http, '../assets/i18n/', '.json'),
                deps: [HttpClient]
            }
        }),
        LibCommonModule.forRoot(environment),
        RouterModule,
        LaddaModule,
        SessionExpiredModule,
        ToastrModule.forRoot()
    ],
    providers: [
        SSOService,
        AccountService,
        {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(private injector: Injector) {
        setAppInjector(this.injector);
    }
}

