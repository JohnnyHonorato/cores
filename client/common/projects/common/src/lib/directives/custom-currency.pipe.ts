import {Pipe, PipeTransform} from '@angular/core';

const padding = '000000';

@Pipe({name: 'customCurrency'})
export class CustomCurrencyPipe implements PipeTransform {

    prefix: string;
    decimalSeparator: string;
    thousandsSeparator: string;
    suffix: string;

    private ptCurrency = {
        prefix: 'R$ ',
        suffix: '',
        decimalSeparator: ',',
        thousandsSeparator: '.',
    };

    private usCurrency = {
        prefix: 'U$ ',
        suffix: '',
        decimalSeparator: '.',
        thousandsSeparator: ',',
    };

    constructor() {
        if (localStorage.getItem('lang') === 'pt') {
            this.prefix = this.ptCurrency.prefix;
            this.suffix = this.ptCurrency.suffix;
            this.decimalSeparator = this.ptCurrency.decimalSeparator;
            this.thousandsSeparator = this.ptCurrency.thousandsSeparator;
        } else {
            this.prefix = this.usCurrency.prefix;
            this.suffix = this.usCurrency.suffix;
            this.decimalSeparator = this.usCurrency.decimalSeparator;
            this.thousandsSeparator = this.usCurrency.thousandsSeparator;
        }
    }

    transform(value: string, fractionSize: number = 2): string {

        if (parseFloat(value) % 1 !== 0) {
            fractionSize = 2;
        }
        let [integer, fraction = ''] = (parseFloat(value).toString() || '').toString().split('.');

        fraction = fractionSize > 0
            ? this.decimalSeparator + (fraction + padding).substring(0, fractionSize) : '';
        integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, this.thousandsSeparator);
        if (isNaN(parseFloat(integer))) {
            integer = '0';
        }
        return this.prefix + integer + fraction + this.suffix;

    }

    parse(value: string, fractionSize: number = 2): string {
        let [integer, fraction = ''] = (value || '').replace(this.prefix, '')
            .replace(this.suffix, '')
            .split(this.decimalSeparator);

        integer = integer.replace(new RegExp(this.thousandsSeparator, 'g'), '');

        fraction = parseInt(fraction, 10) > 0 && fractionSize > 0
            ? this.decimalSeparator + (fraction + padding).substring(0, fractionSize)
            : '';

        return integer + fraction;
    }


}
