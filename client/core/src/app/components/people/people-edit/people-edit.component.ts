import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, Subject} from 'rxjs';
import {BaseEditComponent, ContactInfoComponent, CustomValidators, SelectSearchService} from 'common';
import {OrganizationURL, PeopleURL, RoleURL} from '../../../shared/util/url.domain';

@Component({
    selector: 'core-people-edit',
    templateUrl: './people-edit.component.html',
    styleUrls: ['./people-edit.component.scss']
})
export class PeopleEditComponent extends BaseEditComponent implements OnInit {

    public contactsValid = false;

    public createUser = false;

    public tab = 'people';

    public role;

    public roles: Observable<any[]>;

    public rolesSubject = new Subject<string>();

    public loadingRoles = false;

    public peopleRoles = [];

    public peopleRolesSubject = new Subject<string>();

    public organizations: Observable<any[]>;

    public organizationsSubject = new Subject<string>();

    public loadingOrganizations = false;

    private personNameRegex = '^(((?<!^)\\s)*[A-zÀ-ú]+)+$';

    @ViewChild(ContactInfoComponent, {static: false})
    private contactInfo: ContactInfoComponent;

    collapseOne = false;

    constructor(private route: ActivatedRoute,
                private searchService: SelectSearchService) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.searchService.search('', OrganizationURL.BASE, 5, 'organization.no-permission').subscribe((result) => {
            this.organizations = this.searchService.startSubject(result, this.organizationsSubject, OrganizationURL.BASE, this.loadingOrganizations);
        });
        this.searchService.search('', RoleURL.BASE, 5, 'role.no-permission').subscribe((result) => {
            this.roles = this.searchService.startSubject(result, this.rolesSubject, RoleURL.BASE, this.loadingRoles);
        });
    }

    getFormControls(): {} {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255), Validators.pattern(this.personNameRegex)]),
            nickname: new FormControl(undefined, [Validators.maxLength(50), Validators.pattern(this.personNameRegex)]),
            governmentCode: new FormControl(undefined, [CustomValidators.cpfValidator]),
            peopleType: new FormControl('COLABORATOR', []),
            contacts: new FormControl(undefined, [])
        };
    }

    getRouterURL(): string {
        return '/people';
    }

    getServiceURL(): string {
        return PeopleURL.BASE;
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    postGetItem() {
        const formItem = {};
        Object.keys(this.getFormControls()).forEach(key => {
            formItem[key] = this.item.hasOwnProperty(key) ? this.item[key] : (key === 'contacts' ? [] : undefined);
        });
        this.editForm.patchValue(formItem);
        this.editForm.get('peopleType').setValue('COLABORATOR');

        if (this.item.id) {
            if (this.item.user) {
                this.enableOrRemoveUserForm();
            }
        }
        this.peopleRoles = this.item.user && this.item.user.roles ? this.item.user.roles : [];
        this.checkContactIsValid();
    }

    enableOrRemoveUserForm() {
        if (this.createUser) {
            this.createUser = !this.createUser;
            this.editForm.removeControl('user');
        } else {
            this.editForm.addControl('user',
                new FormGroup({
                    id: new FormControl(this.item.id && this.item.user ? this.item.user.id : undefined, []),
                    username: new FormControl(this.item.id && this.item.user ? this.item.user.username : undefined,
                        [Validators.required, Validators.maxLength(255),
                            CustomValidators.noWhitespaceValidator]),
                    name: new FormControl(this.item.id && this.item.user ? this.item.user.name : undefined,
                        [Validators.maxLength(255)]),
                    role: new FormControl(this.item.id && this.item.user ? this.item.user.role : undefined),
                    roles: new FormArray(this.item.id && this.item.user && this.item.user.roles ? this.getRolesControlsArray() : [])
                })
            );
            this.createUser = !this.createUser;
        }
    }

    getRolesControlsArray() {
        const array: any[] = [];
        if (this.item.user.roles) {
            this.item.user.roles.forEach(element => {
                array.push(new FormGroup({
                    id: new FormControl(element.id),
                    name: new FormControl(element.name)
                }));
            });
        }
        return array;
    }

    setRolesControlsArray() {
        if (this.createUser) {
            const array = this.editForm.get('user').get('roles') as FormArray;
            while (array.length > 0) {
                array.removeAt(0);
            }
            this.peopleRoles.forEach(element => {
                array.push(new FormGroup({
                    id: new FormControl(element.id),
                    name: new FormControl(element.name)
                }));
            });
        }
    }

    onSubmit() {
        this.setRolesControlsArray();
        super.onSubmit();

    }

    setContacts(event: any) {
        this.editForm.get('contacts').setValue(event);
        this.checkContactIsValid();
    }

    checkContactIsValid() {
        if (this.editForm.get('contacts').value.find(contact => contact.emailTag === 'MAIN')) {
            this.contactsValid = true;
        } else {
            this.contactsValid = false;
        }
    }

    getClassHeight(value) {
        if (value < 5 && innerWidth >= 767) {
            return 'h-100';
        } else {
            return 'min-h-100';
        }
    }

    setCardHeight() {
        const contacts = this.editForm.get('contacts').value;
        if (contacts === null || this.tab === 'user' || this.collapseOne) {
            return 'h-100';
        } else {
            switch (this.contactInfo?.selectedTab) {
                case 'email':
                    return this.getClassHeight(this.contactInfo?.amountEmail);
                case 'phone':
                    return this.getClassHeight(this.contactInfo?.amountPhones);
                case 'address':
                    return this.getClassHeight(this.contactInfo?.amountAddresses);
            }
        }
    }
}
