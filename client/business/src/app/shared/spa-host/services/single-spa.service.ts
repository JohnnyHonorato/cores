import {Injectable} from '@angular/core';
import {mountRootParcel, Parcel, ParcelConfig} from 'single-spa';
import {Observable, from} from 'rxjs';

import {tap} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class SingleSpaService {

    private loadedParcels: {
        [appName: string]: Parcel;
    } = {};

    mount(appName: string, domElement: HTMLElement): Observable<unknown> {
        return from(System.import<ParcelConfig>(appName)).pipe(
            tap((app: ParcelConfig) => {
                this.loadedParcels[appName] = mountRootParcel(app, {
                    data: {
                        moduleId: environment.moduleId
                    },
                    domElement
                });
            })
        );
    }

    unmount(appName: string): Observable<unknown> {
        return from(this.loadedParcels[appName].unmount()).pipe(
            tap(() => delete this.loadedParcels[appName])
        );
    }
}
