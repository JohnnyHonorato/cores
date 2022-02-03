import {ChangeDetectorRef, Component, NgZone, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {ModuleURL} from '@shared/util/url.domain';
import {BaseEditComponent, CustomValidators} from 'common';

@Component({
    selector: 'core-module-edit',
    templateUrl: './module-edit.component.html',
    styleUrls: ['./module-edit.component.scss']
})
export class ModuleEditComponent extends BaseEditComponent implements OnInit, OnDestroy {

    private readonly OPEN_MODE_SPA = 'SPA';
    private ngUnsubscribe = new Subject();
    private readonly linkRegex = '^[a-zA-Z0-9]+((?<=https?):\/\/)?[-.a-zA-Z0-9]+(?<![.])(:?((?<=:)[0-9]+|(?<!:)))?(\/?((?<=\/)[-a-zA-Z0-9]+|(?<!\/)))?\/?([?&=]?(?<=[?&=])[-.a-zA-Z0-9]+)?$';
    private readonly cssClassNameRegex = '\.?[a-z]([a-z0-9-]+)?(__([a-z0-9]+-?)+)?(--([a-z0-9]+-?)+){0,2}';
    private readonly multipleCssClassNameRegex = '^(((?<!^)\s)*' + this.cssClassNameRegex + ')+$';

    isSpaOpenModeSelected: boolean;

    constructor(
        private readonly route: ActivatedRoute,
        private ngZone: NgZone,
        private cdr: ChangeDetectorRef
    ) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.editForm.get('openMode').valueChanges
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe((value) => {
                this.isSpaOpenModeSelected = (value === this.OPEN_MODE_SPA);
                this.ngZone.run(() => {
                    if (this.isSpaOpenModeSelected) {
                        this.editForm.controls.pathName.setValidators([Validators.required, Validators.pattern('^[a-zA-Z]+')]);
                    } else {
                        this.editForm.controls.pathName.setValidators([]);
                        this.editForm.controls.pathName.setErrors(null);
                        this.editForm.controls.pathName.setValue(null);
                    }
                    this.cdr.detectChanges();
                });
            });
    }

    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

    getFormControls(): any {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            link: new FormControl(undefined, [Validators.required, Validators.maxLength(255), Validators.pattern(this.linkRegex)]),
            icon: new FormControl(undefined, [Validators.required, Validators.maxLength(30), Validators.pattern(this.multipleCssClassNameRegex)]),
            openMode: new FormControl(undefined, [Validators.required]),
            pathName: new FormControl(undefined, []),
            visible: new FormControl(true, []),
            permissions: new FormArray([])
        };
    }

    getRouterURL(): string {
        return '/modules';
    }

    getServiceURL(): string {
        return ModuleURL.BASE;
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    addPermission(control) {
        return (control.controls.permissions).push(
            new FormGroup({
                id: new FormControl(undefined, []),
                name: new FormControl(undefined, [Validators.required, CustomValidators.noWhitespaceValidator, Validators.maxLength(255)]),
                label: new FormControl(undefined, [Validators.required, CustomValidators.noWhitespaceValidator, Validators.maxLength(255)]),
                description: new FormControl(undefined, [Validators.required, CustomValidators.noWhitespaceValidator, Validators.maxLength(255)])
            })
        ) as FormArray;
    }

    removePermission(index) {
        const item = (this.editForm.get('permissions') as FormArray).at(index).value;
        if (item && item.id) {
            this.service.remove(`core/v1/permissions`, item.id).subscribe(result => {
                this.removePermissionInList(index);
            }, (response) => {
                this.notification.error(response.error.message);
            });
        } else {
            this.removePermissionInList(index);
        }
    }

    private removePermissionInList(index) {
        (this.editForm.controls.permissions as FormArray).removeAt(index);
        this.notification.deleteSuccess();
    }

    permissionControls(form) {
        return form.controls.permissions.controls;
    }

    postGetItem() {
        super.postGetItem();
        if (this.item.permissions && this.item.permissions.length) {
            this.item.permissions.forEach((permission) => {
                (this.editForm.controls.permissions as FormArray).push(
                    new FormGroup({
                        id: new FormControl(permission.id, []),
                        name: new FormControl(permission.name, [Validators.required, CustomValidators.noWhitespaceValidator]),
                        label: new FormControl(permission.label, [Validators.required, CustomValidators.noWhitespaceValidator]),
                        description: new FormControl(permission.description, [Validators.required, CustomValidators.noWhitespaceValidator])
                    })
                );
            });
        }
    }
}
