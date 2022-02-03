import {BrowserModule} from '@angular/platform-browser';
import {Injector, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SSOService} from './shared/services/sso.service';
import {OAuthModule} from 'angular-oauth2-oidc';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AccountService} from './shared/services/account.service';
import {setAppInjector} from './shared/util/app.injector';
import {TranslateService} from './shared/services/translate.service';
import {ModuleConfigService} from './shared/services/module-config.service';
import {NgxPermissionsModule} from 'ngx-permissions';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {SessionExpiredModule} from './components/session-expired/session-expired.module';
import {HeaderModule} from './shared/components/header/header.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FooterComponent} from './shared/components/footer/footer.component';
import {MainComponent} from './components/main/main.component';
import {SidebarModule} from './components/sidebar/sidebar.module';
import {TokenInterceptor} from './shared/util/token.interceptor';

@NgModule({
    declarations: [
        AppComponent,
        FooterComponent,
        MainComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        OAuthModule.forRoot(),
        HttpClientModule,
        NgxPermissionsModule.forRoot(),
        SessionExpiredModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (http: HttpClient) => new TranslateHttpLoader(http, './assets/i18n/', '.json'),
                deps: [HttpClient]
            }
        }),
        HeaderModule,
        SidebarModule,
        FlexLayoutModule
    ],
    providers: [
        SSOService,
        AccountService,
        TranslateService,
        ModuleConfigService,
        {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(private injector: Injector) {
        setAppInjector(this.injector);
    }
}
