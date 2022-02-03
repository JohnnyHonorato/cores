import {Component, HostListener, OnInit} from '@angular/core';
import {SSOService} from '@shared/services/sso.service';
import {AccountService} from '@shared/services/account.service';
import {NgxPermissionsService} from 'ngx-permissions';
import {TranslateService} from '@ngx-translate/core';
import {ModuleConfigService} from 'common';
import {environment} from '@environments/environment';

@Component({
    selector: 'core-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    innerWidth;

    expanded = false;

    isLogged = false;

    constructor(
        private ssoService: SSOService,
        private accountService: AccountService,
        private permissionService: NgxPermissionsService,
        private ngxTranslate: TranslateService,
        private common: ModuleConfigService
    ) {
        this.common.API_KEY = environment.wso2.coreApikey;
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
    onResize(event?) {
        this.innerWidth = window.innerWidth;
        this.innerWidth <= 767 ? this.expanded = true : this.expanded = false;
    }

    private configTranslate() {
        this.ngxTranslate.addLangs(['pt']);
        this.ngxTranslate.setDefaultLang('pt');
        this.ngxTranslate.use('pt');
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

