import {Component, HostListener, OnInit} from '@angular/core';
import {ModuleConfigService} from 'common';
import {TranslateService} from '@ngx-translate/core';
import {SSOService} from '@shared/service/sso.service';
import {AccountService} from '@shared/service/account.service';
import {NgxPermissionsService} from 'ngx-permissions';
import {environment} from '@environments/environment';
import moment from 'moment';
import {registerLocaleData} from '@angular/common';
import localePt from '@angular/common/locales/pt';

@Component({
    selector: 'business-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    innerWidth;

    expanded = false;

    isLogged = false;

    constructor(
        private ngxTranslate: TranslateService,
        private common: ModuleConfigService,
        private ssoService: SSOService,
        private accountService: AccountService,
        private permissionService: NgxPermissionsService
    ) {
        this.common.API_KEY = environment.wso2.businessApikey;
        this.configTranslate();
    }

    ngOnInit(): void {
        this.isLogged = this.ssoService.isLoggedIn();
        if (!this.isLogged) {
            this.ssoService.obtainAccessToken();
        }
        this.getUserPermissions();
        this.onResize();
    }

    @HostListener('window:resize', ['$event'])
    onResize(event ?) {
        this.innerWidth = window.innerWidth;
        this.innerWidth <= 767 ? this.expanded = true : this.expanded = false;
    }

    private configTranslate() {
        localStorage.setItem('lang', 'pt');
        this.ngxTranslate.addLangs(['pt']);
        this.ngxTranslate.setDefaultLang('pt');
        this.ngxTranslate.use('pt');
        moment.locale('pt-br');
        registerLocaleData(localePt, 'pt');
    }

    private getUserPermissions() {
        this.accountService.getPermissions()
            .subscribe((result: any) => {
                if (result) {
                    sessionStorage.setItem('permissions', JSON.stringify(result));
                    const permissions = result.map((permission) => {
                        return permission.name;
                    });
                    this.permissionService.loadPermissions(permissions);
                    this.common.setPermissions(permissions);
                }
            });
    }
}
