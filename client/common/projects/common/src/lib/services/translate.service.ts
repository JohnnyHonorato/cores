import {Injectable} from '@angular/core';

import {TranslateService as NgxTranslateService} from '@ngx-translate/core';

@Injectable()
export class TranslateService {

    constructor(private translate: NgxTranslateService) {
    }

    public setLang(lang: any = null): void {
        lang = lang ? lang : localStorage.getItem('lang');
        if (!lang) {
            lang = this.translate.getBrowserCultureLang().toLowerCase();
        }
        // TODO use default english when implemented
        // lang = ['en', 'pt'].includes(lang) ? lang : 'en';
        lang = ['pt'].includes(lang) ? lang : 'pt';
        this.translate.use(lang);
        this.saveLang(lang);
    }

    public getLang() {
        const lang = localStorage.getItem('lang');
        if (lang) {
            return lang;
        }
        return this.translate.getBrowserLang();
    }

    protected saveLang(lang: any): void {
        localStorage.setItem('lang', String(lang));
    }

}
