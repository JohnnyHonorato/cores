import { Injectable } from '@angular/core';

import { BaseService } from './base.service';
import { HttpParams } from '@angular/common/http';

/**
 * The 'SearchService' class provides the common API and operations to
 * retrieve, find, filter and list items.
 *
 * @extends BaseService
 */
@Injectable()
export class SearchService extends BaseService {
    /**
     * Constructor.
     */
    constructor() {
        super();
    }

    /**
     * Gets all items.
     *
     * @param url - The url of backend service
     * @returns The result of the search method.
     */
    getAll(url) {
        return this.search(url);
    }

    /**
     * Searches the items where the filters matches.
     *
     * @param url - The url of backend service
     * @param pagination - The pagination object
     * @param filters - The filters to search
     * @returns The result values of a paginated and filtered search at the
     * backend services
     */
    search(url, pagination = {}, filters = []): any {
        const params = new HttpParams().set(
            'filter',
            JSON.stringify(Object.assign(pagination, { filters }))
        );

        return this.get(url, params);
    }

    /**
     * Gets one item by its ID.
     *
     * @param url - The url of backend service
     * @param id - The id of the iten
     * @returns The item by its id
     */
    getOne(url, id) {
        return this.get(url + '/' + id);
    }

}
