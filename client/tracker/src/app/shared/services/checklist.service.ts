import {Injectable} from '@angular/core';
import {BaseService} from 'common';
import {environment} from 'src/environments/environment';
import {ChecklistURL} from '../util/url.domain';

@Injectable({
  providedIn: 'root'
})
export class ChecklistService extends BaseService {

    constructor() {
        super();
    }

    getApiKey(): string {
        return environment.wso2.trackerApikey;
    }

    deleteChecklist(checklistId: number) {
        return this.delete(`${ChecklistURL.BASE}/${checklistId}`);
    }

    deleteChecklistItem(checklistId: number, checklistItemId: number) {
        return this.delete(`${ChecklistURL.BASE}/${checklistId}/item/${checklistItemId}`);
    }

}
