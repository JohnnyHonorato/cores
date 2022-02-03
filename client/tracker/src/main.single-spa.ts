import {enableProdMode, NgZone} from '@angular/core';

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {NavigationStart, Router} from '@angular/router';
import {environment} from '@environments/environment';
import {getSingleSpaExtraProviders, singleSpaAngular} from 'single-spa-angular';
import {AppModule} from './app/app.module';
// @ts-ignore
import {singleSpaPropsSubject} from '@single-spa/single-spa-props';

if (environment.production) {
    enableProdMode();
}

const lifecycles = singleSpaAngular({
    bootstrapFunction: singleSpaProps => {
        singleSpaPropsSubject.next(singleSpaProps);
        return platformBrowserDynamic(getSingleSpaExtraProviders()).bootstrapModule(AppModule);
    },
    template: '<tracker-root />',
    Router,
    NgZone,
    NavigationStart,
});

export const bootstrap = lifecycles.bootstrap;
export const mount = lifecycles.mount;
export const unmount = lifecycles.unmount;
