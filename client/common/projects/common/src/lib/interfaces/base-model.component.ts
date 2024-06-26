import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CrudService} from '../services/crud.service';
import {AppInjector} from '../util/app.injector';
import {BaseComponent} from './base.component';

/**
 * The 'BaseModelComponent' class provides the common API for all the components
 * that works with models.
 *
 * All components that uses models MUST extend this class.
 *
 * @extends BaseComponent
 */
@Component({
    template: ''
})
export abstract class BaseModelComponent extends BaseComponent
    implements OnInit {
    /**
     * Service to do the CRUD operations.
     */
    protected service: CrudService = AppInjector.get(CrudService);

    /**
     * Constructor.
     */
    constructor() {
        super();
    }

    /**
     * On Init of the components.
     */
    ngOnInit(): void {
        super.ngOnInit();
    }

    /**
     * Gets the param from the activated route.
     *
     * @param param - The param that will be verified if is in the param list
     * @returns The param from the activated route.
     */
    protected getParam(param: string): string {
        return this.getActivatedRoute()
            ? this.getActivatedRoute().snapshot.paramMap.get(param)
            : null;
    }

    /**
     * Gets the activated route for data extraction.
     *
     * @returns The current activated route
     */
    protected getActivatedRoute(): ActivatedRoute {
        return null;
    }

    /**
     * Gets the base URL of the service (mainly backend url).
     *
     * Ex: To get all items, like 'user', in server, the url is: http://server.com/user
     * This method should return just your base: 'user'.
     *
     * @returns The base URL of the service.
     */
    abstract getServiceURL(): string;

    /**
     * Gets the base URL of the router (frontend url).
     *
     * Ex: To navigate between 'area' components, like 'area/edit', should return 'area'.
     *
     * @returns The base URL of the router
     */
    abstract getRouterURL(): string;

    getService() {
        return this.service;
    }

}
