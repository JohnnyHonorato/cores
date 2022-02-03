import {Component, OnInit} from '@angular/core';
import {SSOService} from '@shared/services/sso.service';
import {AccountService} from '@shared/services/account.service';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

    public isLogged = false;

    public loading = false;

    public modules: any;

    constructor(
        private ssoService: SSOService,
        private accountService: AccountService,
    ) {
    }

    ngOnInit(): void {
        this.checkIsLogged();
    }

    private checkIsLogged() {
        this.isLogged = this.ssoService.isLoggedIn();
        if (this.isLogged) {
            this.getUserModules();
        } else {
            this.ssoService.obtainAccessToken();
        }
    }

    private getUserModules() {
        this.loading = true;
        this.accountService.getModules().subscribe(response => {
            this.modules = response;
            this.loading = false;
        });
    }

    goTo(link: any) {
        window.open(link, '_blank');
    }

}
