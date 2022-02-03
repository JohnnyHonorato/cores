import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import 'rxjs/add/operator/do';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(
        private router: Router) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).do(() => {
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401 || err.status === 0) {
                    window.setTimeout(() => {
                    }, 100);
                }
                // @ts-ignore
                if (err.status === 401 && !(err.url.includes('login'))) {
                    window.setTimeout(() => {
                        this.router.navigate(['session-expired']);
                    }, 1000);
                } else if (err.status === 0) {
                    window.setTimeout(() => {
                    }, 1000);
                }
            }
        });
    }
}
