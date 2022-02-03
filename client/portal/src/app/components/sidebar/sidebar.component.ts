import {Component, HostBinding, Input, OnInit} from '@angular/core';
import {SSOService} from '../../shared/services/sso.service';
import {AccountService} from '../../shared/services/account.service';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    @Input() @HostBinding('class.expanded') expanded = false;

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
