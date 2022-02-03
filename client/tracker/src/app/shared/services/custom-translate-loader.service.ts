import {Injectable} from '@angular/core';
import {TranslateLoader} from '@ngx-translate/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '@environments/environment';

@Injectable({providedIn: 'root'})
export class CustomTranslateLoaderService implements TranslateLoader {

    contentHeader = new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
    });

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getTranslation(lang: string): Observable<any> {
        const apiAddress = environment.urlClient + `/assets/i18n/${lang}.json`;
        return this.httpClient.get(apiAddress, {headers: this.contentHeader}).pipe(
            // TODO use default english when implemented
            catchError(_ => this.httpClient.get(`/assets/i18n/pt.json`))
        );
    }
}
