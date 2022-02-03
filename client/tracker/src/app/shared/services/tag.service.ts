import {Injectable} from '@angular/core';
import {BaseService} from 'common';
import { environment } from 'src/environments/environment';

@Injectable()
export class TagService extends BaseService {

    constructor() {
        super();
    }

    getApiKey(): string {
        return environment.wso2.trackerApikey;
    }

    saveTag(idTrackerModel, tagModel){
        return this.post(`tracker/v1/tracker-models/${idTrackerModel}/tags`, tagModel);
    }

    removeTag(idTrackerModel, idTag) {
        return this.delete(`tracker/v1/tracker-models/${idTrackerModel}/tags/${idTag}`);
    }

    getTags(idTrackerModel) {
        return this.get(`tracker/v1/tracker-models/${idTrackerModel}/tags/all`);
    }

    getTag(idTrackerModel, idTag) {
        return this.get(`tracker/v1/tracker-models/${idTrackerModel}/tags/${idTag}`);
    }
}
