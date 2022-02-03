import {ChangeDetectionStrategy, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SingleSpaService} from './services/single-spa.service';
import {Observable} from 'rxjs';

@Component({
    selector: 'business-spa-host',
    template: '<div #business></div>',
    styles: ['div { height: 100% !important }'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SpaHostComponent implements OnInit {

    constructor(
        private singleSpaService: SingleSpaService,
        private route: ActivatedRoute
    ) {
    }

    @ViewChild('business', {static: true})
    businessRef: ElementRef;

    appName: string;

    ngOnInit() {
        this.appName = this.route.snapshot.data.app;
        this.mount().subscribe();
    }

    mount(): Observable<unknown> {
        // TODO verficar tratamento de errro
        return this.singleSpaService.mount(this.appName, this.businessRef.nativeElement);
    }

    unmount(): Observable<unknown> {
        return this.singleSpaService.unmount(this.appName);
    }

}
