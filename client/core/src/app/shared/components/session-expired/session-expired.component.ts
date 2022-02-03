import {Component, OnInit} from '@angular/core';
import {SSOService} from '../../services/sso.service';

@Component({
    selector: 'core-session-expired',
    templateUrl: './session-expired.component.html',
    styleUrls: ['./session-expired.component.scss']
})
export class SessionExpiredComponent implements OnInit {

    constructor(
        private ssoService: SSOService
    ) {
    }

    ngOnInit(): void {
        setTimeout(() => {
            this.ssoService.logout();
        }, 2000);
    }

}
