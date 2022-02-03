import {Component, OnInit} from '@angular/core';
import {BaseEditComponent, CustomValidators} from 'common';
import {FormControl, Validators} from '@angular/forms';
import {RoleURL} from '@shared/util/url.domain';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'core-role-edit',
    templateUrl: './role-edit.component.html',
    styleUrls: ['./role-edit.component.scss']
})
export class RoleEditComponent extends BaseEditComponent implements OnInit {

    constructor(private route: ActivatedRoute) {
        super();
    }

    ngOnInit() {
        super.ngOnInit();
    }

    getFormControls(): {} {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            permissions: new FormControl([], [])
        };
    }

    getRouterURL(): string {
        return 'roles';
    }

    getServiceURL(): string {
        return RoleURL.BASE;
    }

    getActivatedRoute() {
        return this.route;
    }
}
