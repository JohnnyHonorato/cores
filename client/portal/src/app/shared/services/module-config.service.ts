import {Injectable} from '@angular/core';
import {NgxPermissionsService} from 'ngx-permissions';

@Injectable()
export class ModuleConfigService {

    public API_KEY: any;

    constructor(private xPermissions: NgxPermissionsService) {
    }

    public setPermissions(permissions: string[]) {
        this.xPermissions.loadPermissions(permissions);
    }

}
