import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {BaseEditComponent, CustomValidators, FileService} from 'common';
import {OrganizationURL} from '@shared/util/url.domain';

@Component({
    selector: 'business-organization-edit',
    templateUrl: './organization-edit.component.html',
    styleUrls: ['./organization-edit.component.scss']
})
export class OrganizationEditComponent extends BaseEditComponent implements OnInit {

    public view = false;
    public contactsValid = false;
    public existsContactInfo = false;
    public cnpjVerified = false;
    public expandLeadsTable = false;

    public fileInformation: any = '';
    private deletedFileName = '';

    collapseOne = false;
    collapseTwo = false;

    constructor(
        private route: ActivatedRoute,
        private fileService: FileService
    ) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
    }

    protected getFormValue() {
        const formValue = this.editForm.value;
        delete formValue.emails;
        delete formValue.phones;
        delete formValue.addresses;
        return formValue;
    }

    getRouterURL(): string {
        return '/organizations';
    }

    getServiceURL(): string {
        return OrganizationURL.BASE;
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    leadCreated(event: any) {
        this.editForm.get('peopleOrganizations').setValue(event);
    }

    getFormControls(): object {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            fantasyName: new FormControl(undefined, [Validators.required,
                Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            governmentCode: new FormControl(undefined, [Validators.required, CustomValidators.cnpjValidator]),
            contacts: new FormControl(undefined, []),
            peopleOrganizations: new FormControl(undefined, []),
            description: new FormControl(undefined, [Validators.maxLength(5000)]),
            filePath: new FormGroup({
                id: new FormControl(undefined, []),
                name: new FormControl(undefined, []),
                directory: new FormControl(undefined, []),
                deleted: new FormControl(false, []),
            })
        };
    }

    postGetItem() {
        const formItem = {};
        this.view = true;
        this.cnpjVerified = true;
        Object.keys(this.getFormControls()).forEach(key => {
            formItem[key] = this.item.hasOwnProperty(key) ? this.item[key] : undefined;
        });
        this.editForm.patchValue(formItem);
        if (this.item.id) {
            this.expandTables(this.item);
            this.checkContactIsValid();
        }

    }

    private expandTables(item) {
        if (this.item.contacts && this.item.contacts.length > 0) {
            this.existsContactInfo = true;
        }
        for (const i in item.peopleOrganizations) {
            if (item.peopleOrganizations[i].people) {
                this.expandLeadsTable = true;
                break;
            }
        }
    }

    getOrganizationByCnpj() {
        if (this.editForm.get('governmentCode').invalid) {
            return;
        }

        const governmentCode = this.editForm.get('governmentCode').value;
        if (governmentCode != null) {
            const unmaskedCNPJ = governmentCode.replace(/[\.|\/|\- ]/g, '');
            const URL = `${this.getServiceURL()}/cnpj/${unmaskedCNPJ}`;
            this.service.get(URL).subscribe(
                (organization: any) => {
                    if (organization.id) {
                        this.editForm.reset();
                        this.editForm.patchValue(organization);
                        this.editForm.get('governmentCode').setValue(governmentCode);
                        this.cnpjVerified = true;
                        this.item = organization;
                        this.expandTables(this.item);
                    }
                },
                (err) => {
                    if (err.status === 400) {
                        this.cnpjVerified = true;
                        this.item = {};
                        this.existsContactInfo = false;
                        this.expandLeadsTable = false;
                        this.editForm.reset();
                        this.editForm.get('governmentCode').setValue(governmentCode);
                    }
                }
            );
        }
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

    saveImg($event) {
        this.fileInformation = $event;
    }

    get filePath() {
        return this.editForm.get('filePath').value ? this.editForm.get('filePath').value : null;
    }

    deleteImg() {
        this.fileInformation = undefined;
        if (this.isEditMode) {
            this.deleteImageFromServer();
        }
    }

    deleteImageFromServer() {
        this.deletedFileName = this.filePath.name;
        this.editForm.get('filePath').get('deleted').setValue(true);
        this.editForm.get('filePath').get('name').setValue(null);
        this.editForm.get('filePath').get('directory').setValue(null);
    }

    preSubmitOrganization() {
        if (this.fileInformation) {
            this.fileService.upload(this.fileInformation.file, this.fileInformation.contentType).subscribe((result: any) => {
                this.editForm.get('filePath').get('name').setValue(result.name);
                this.editForm.get('filePath').get('directory').setValue(result.directory);
                this.editForm.get('filePath').get('deleted').setValue(false);
                super.onSubmit();
            }, (error: any) => {
                this.deleteImageFromServer();
            });
        } else {
            if (this.filePath.deleted && this.deletedFileName != null) {
                this.fileService.deleteFile(this.deletedFileName).subscribe(result => {
                    super.onSubmit();
                });
            } else {
                super.onSubmit();
            }
        }
    }

}
