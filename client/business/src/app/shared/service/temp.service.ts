import {SearchService} from 'common';
import {Injectable} from '@angular/core';
import {environment} from '@environments/environment';

@Injectable()
export class TempService extends SearchService {

    protected getApiKey(): string {
        return environment.wso2.coreApikey;
    }
}
