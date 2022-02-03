import {Injectable} from '@angular/core';
import {NgxPermissionsService} from 'ngx-permissions';

@Injectable()
export class ModuleConfigService {

    public API_KEY;

    constructor(private xPermissions: NgxPermissionsService) {
    }

    public setPermissions(permissions) {
        this.xPermissions.loadPermissions(permissions);
    }

}
