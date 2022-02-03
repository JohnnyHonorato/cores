import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '@environments/environment';

@Injectable({
    providedIn: 'root'
})
export class CurrencyService {

    constructor(private http: HttpClient) {
    }

    getAllCurrencies() {
        return this.http.get(environment.urlClient + '/assets/database/currency/currency_codes.json');
    }
}
