import { Injectable } from '@angular/core';

import { SearchService } from './search.service';
import { Observable } from 'rxjs';

/**
 * The 'CrudService' class provides the common API and operations
 * to insert, update and remove an item.
 *
 * @extends SearchService
 */
@Injectable()
export class CrudService extends SearchService {

    /**
     * Constructor.
     */
    constructor() {
        super();
    }

    /**
     * Inserts an item.
     *
     * @param url - The API resource endpoint
     * @param item - The object to be sent
     * @returns The response of the HTTP call
     */
    insert(url: string, item: object): Observable<object> {
        return this.post(url, item);
    }

    /**
     * Updates the item.
     *
     * @param url - The API resource endpoint
     * @param id - The identificator of the object
     * @param item - Theobject to be sent
     * @returns The response of the HTTP call
     */
    update(url: string, id: any, item: object): Observable<object> {
        return this.put(url + '/' + id, item);
    }

    /**
     * Updates the item partially.
     *
     * @param url - The API resource endpoint
     * @param id - The Identificator of the object
     * @param item - The object to be sent
     * @returns The response of the HTTP call
     */
    updatePartial(url: string, id: number, item: object): Observable<object> {
        return this.patch(url + '/' + id, item);
    }

    /**
     * Removes the item.
     *
     * @param url - The API resource endpoint
     * @param id - The identificator of the object to be removed
     * @returns The response of the HTTP call
     */
    remove(url: string, id: number) {
        return this.delete(url + '/' + id);
    }
}
