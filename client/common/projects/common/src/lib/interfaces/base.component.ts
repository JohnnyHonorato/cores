import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {NotificationService} from '../services/notification.service';
import {TranslateService} from '@ngx-translate/core';
import {AppInjector} from '../util/app.injector';
import {AbstractControl} from '@angular/forms';

declare var jQuery: any;

/**
 * The 'BaseComponent' class provides the common API for all the components
 * in the system.
 *
 * Operations like notification, navigation and other are already implemented.
 *
 * All components MUST extend this class.
 */
@Component({
    template: ''
})
export abstract class BaseComponent implements OnInit, AfterViewInit {

    /**
     * Provides the navigation and url manipulation capabilities.
     */
    protected router: Router = AppInjector.get(Router);

    /**
     * Module for user notification.
     */
    protected notificationService: NotificationService = AppInjector.get(NotificationService);

    /**
     * Module for i18n.
     */
    protected translateService: TranslateService = AppInjector.get(TranslateService);

    /**
     * Constructor.
     */
    constructor() {
    }

    /**
     * On Init of the components.
     */
    ngOnInit(): void {
        this.applyMasks();
    }

    ngAfterViewInit(): void {

    }


    /**
     * Navigates to the path provided.
     *
     * @param urls - The urls array
     */
    protected navigate(urls: any[]): void {
        this.router.navigate(urls);
    }

    /**
     * Gets the notification module.
     *
     * @returns The notification module instance
     */
    get notification() {
        return this.notificationService;
    }

    /**
     * Gets the translated message.
     *
     * @returns The translated message
     */
    protected translate(code: string): string {
        return this.translateService.instant(code);
    }

    public hasError(control: AbstractControl): string {
        return control && control.touched && control.invalid ? 'is-invalid state-invalid' : '';
    }

    /**
     * Gets the translated message.
     *
     * @returns The instant translated message
     */
    protected translateInstant(code: string): string {
        return this.translateService.instant(code);
    }

    protected applyMasks(): void {
        window.setTimeout(() => {
            jQuery.applyDataMask(':input');
        }, 200);
    }

}
