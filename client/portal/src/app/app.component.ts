import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    expanded = false;

    constructor(
        private ngxTranslate: TranslateService,
    ) {
        this.configTranslate();
    }

    private configTranslate() {
        this.ngxTranslate.addLangs(['pt']);
        this.ngxTranslate.setDefaultLang('pt');
        this.ngxTranslate.use('pt');
    }


}
