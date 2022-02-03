import {Component, OnInit} from '@angular/core';
import {ModuleConfigService} from 'common';
import {TranslateService} from '@ngx-translate/core';
import {environment} from '@environments/environment';
import {NgxPermissionsService} from 'ngx-permissions';

@Component({
    selector: 'tracker-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    constructor(
        private common: ModuleConfigService,
        private ngxPermissionService: NgxPermissionsService,
        private ngxTranslate: TranslateService,
    ) {
        this.common.API_KEY = environment.wso2.trackerApikey;
        this.configTranslate();
    }

    ngOnInit(): void {
        this.loadPermissions();
    }

    private loadPermissions() {
        const localPermissions = JSON.parse(sessionStorage.getItem('permissions'));
        const permissions = localPermissions.map((permission) => {
            return permission.name;
        });
        this.ngxPermissionService.loadPermissions(permissions);
        this.common.setPermissions(permissions);
    }

    private configTranslate() {
        this.ngxTranslate.addLangs(['pt']);
        this.ngxTranslate.setDefaultLang('pt');
        this.ngxTranslate.use('pt');
    }


}
