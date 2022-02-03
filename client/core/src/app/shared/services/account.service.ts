import {Injectable} from '@angular/core';
import {environment} from '@environments/environment';
import {AccountURL, SERVER_URL, UserURL} from '../util/url.domain';
import {BaseService} from 'common';

@Injectable()
export class AccountService extends BaseService {

    constructor() {
        super();
    }

    public getModules() {
        return this.get(`${AccountURL.BASE}/modules`);
    }

    public getPermissions() {
        return this.get(AccountURL.BASE + '/permissions');
    }

    public setChangePassword(token: any, body: object | undefined) {
        return this.post(`${UserURL.BASE}/change-password?token=${token}`, body);
    }

    getApiKey(): string {
        return environment.wso2.coreApikey;
    }

    protected getServerURL(): string {
        return SERVER_URL;
    }
}
