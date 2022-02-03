import {BaseService} from 'common';
import {Injectable} from '@angular/core';

@Injectable()
export class FilterService extends BaseService {

    constructor() {
        super();
    }

    createFilter(id, filterObject) {
        return this.post(`tracker/v1/tracker-models/${id}/filters`, filterObject);
    }

    updateFilter(id, filterObject) {
        return this.put(`tracker/v1/tracker-models/${id}/filters/${filterObject.id}`, filterObject);
    }
}
