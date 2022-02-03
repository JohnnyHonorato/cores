import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {environment} from '@environments/environment';
import {SSOService} from '../../services/sso.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

    @Output() toggle = new EventEmitter<string>();

    constructor(
        private ssoService: SSOService
    ) {
    }

    ngOnInit(): void {
    }

    logout() {
        this.ssoService.logout();
    }

    get user() {
        // TODO
        return {name: 'Sample'};
    }

    toggleMenu() {
        this.toggle.emit();
    }

    gotToPortal() {
        window.open(environment.portalAppLink, '_self');
    }
}
