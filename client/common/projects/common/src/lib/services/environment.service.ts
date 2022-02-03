import {Inject, Injectable} from '@angular/core';

@Injectable()
export class EnvironmentService {

    constructor(@Inject('env') private env: any) {}

    public getServerUrl(): string {
        return this.env.url;
    }

    public getCoreApiKey(): string {
        return this.env.wso2.coreApikey;
    }
}
