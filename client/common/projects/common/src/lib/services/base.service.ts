import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {AppInjector} from '../util/app.injector';
import {TranslateService} from './translate.service';
import * as moment from 'moment';
import {Observable} from 'rxjs';
import {ModuleConfigService} from './module-config.service';
import {EnvironmentService} from './environment.service';

/**
 * The 'BaseService' class provides the common API for all services.
 *
 * The HTTP request operation are already implemented.
 *
 * All services MUST extend this class.
 */
export abstract class BaseService {

    /**
     * Client for the HTTP operations.
     */
    protected moduleConfigService: ModuleConfigService = AppInjector.get(ModuleConfigService);

    /**
     * Client for the HTTP operations.
     */
    protected http: HttpClient = AppInjector.get(HttpClient);

    /**
     * Service to translate messages.
     */
    protected translateService: TranslateService = AppInjector.get(TranslateService);

    /**
     * Service to get the environment variables.
     */
    protected environment: EnvironmentService = AppInjector.get(EnvironmentService);

    /**
     * Constructor.
     */
    constructor() {}

    /**
     * HTTP Method GET.
     *
     * @param url - The API resource endpoint
     * @param params - The url HTTP params to go in the endpoint
     * @returns The response of the HTTP call
     */
    get(url: string, params: HttpParams = null): Observable<object> {
        return this.http.get(this.getServerURL() + url, {
            headers: this.getHeaders(),
            params
        });
    }

    /**
     * HTTP Method POST.
     *
     * @param url - The API resource endpoint
     * @param body - The object to be sent
     * @returns The response of the HTTP call
     */
    post(url: string, body: object = {}) {
        return this.http.post(this.getServerURL() + url, body, {
            headers: this.getHeaders()
        });
    }

    /**
     * HTTP Method PUT.
     *
     * @param url - The API resource endpoint
     * @param body - The object to be sent
     * @returns The response of the HTTP call
     */
    put(url: string, body: object = {}): Observable<object> {
        return this.http.put(this.getServerURL() + url, body, {
            headers: this.getHeaders()
        });
    }

    /**
     * HTTP Method PATCH.
     *
     * @param url - The API resource endpoint
     * @param body - The object to be sent
     * @returns The response of the HTTP call
     */
    patch(url: string, body: object = {}): Observable<object> {
        return this.http.patch(this.getServerURL() + url, body, {
            headers: this.getHeaders()
        });
    }

    /**
     * HTTP Method DELETE.
     *
     * @param url - The API resource endpoint
     * @returns The response of the HTTP call
     */
    delete(url: string) {
        return this.http.delete(this.getServerURL() + url, {headers: this.getHeaders()});
    }

    /**
     * Executes before the request.
     *
     * @param httpHeaders - The HTTP headers to be customized by the child
     */
    protected customHeaders(httpHeaders: HttpHeaders): void {
    }

    /**
     * Gets the default headers to request the server.
     *
     * @returns - The HTTP Headers object created
     */
    protected getHeaders(): HttpHeaders {
        let httpHeaders: HttpHeaders;
        const idToken = sessionStorage.getItem('id_token');

        if (idToken) {
            httpHeaders = new HttpHeaders()
                .set('apikey', this.getApiKey())
                .set('Authorization', `Bearer ${idToken}`)
                .set('Accept-Language', this.translateService.getLang());
        } else {
            httpHeaders = new HttpHeaders()
                .set('Accept-Language', this.translateService.getLang())
                .set('apikey', this.getApiKey());
        }

        this.customHeaders(httpHeaders);
        return httpHeaders;
    }

    protected getApiKey(): string {
        return this.moduleConfigService.API_KEY;
    }

    protected getServerURL(): string {
        return this.environment.getServerUrl();
    }

    getParams(): HttpParams {
        let httpParams: HttpParams = new HttpParams();

        httpParams = httpParams.set('time', moment().unix().toString());

        return httpParams;
    }



}
