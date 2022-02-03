import {ErrorHandler, Injectable, NgZone} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
    constructor(
        private router: Router,
        private zone: NgZone
    ) {}

    handleError(error: any) {
        // Check if it's an error from an HTTP response
        if (!(error instanceof HttpErrorResponse)) {
            error = error.rejection; // get the error object
        }
        this.zone.run(() =>
            this.router.navigate(['/not-found'])
        );

        console.error('Error from global error handler', error);
    }
}
