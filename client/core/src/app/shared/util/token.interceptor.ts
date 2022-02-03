import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import 'rxjs/add/operator/do';
import {Observable} from 'rxjs/Observable';
import {TranslateService} from '@ngx-translate/core';
import {NotificationService} from 'common';
import {Router} from '@angular/router';

@Injectable()
export default class TokenInterceptor implements HttpInterceptor {

    constructor(
        private notification: NotificationService,
        private translateService: TranslateService,
        private router: Router) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).do(() => {
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401 || err.status === 0) {
                    window.setTimeout(() => {
                        this.notification.clear();
                    }, 100);
                }
                if (err.status === 401 && !(err.url.includes('login'))) {
                    window.setTimeout(() => {
                        this.router.navigate(['session-expired']);
                    }, 1000);
                } else if (err.status === 500) {
                    window.setTimeout(() => {
                        this.router.navigate(['session-expired']);
                    }, 1000);
                } else if (err.status === 0) {
                    window.setTimeout(() => {
                        this.notification.error(this.translateService.instant('system.server-off'));
                    }, 1000);
                }
            }
        });
    }
}
