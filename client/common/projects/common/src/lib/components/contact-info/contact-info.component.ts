import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BaseComponent} from '../../interfaces/base.component';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from '../../util/custom-validator';

@Component({
    selector: 'common-contact-info',
    templateUrl: './contact-info.component.html',
    styleUrls: ['./contact-info.component.scss']
})
export class ContactInfoComponent extends BaseComponent implements OnInit {

    @Input() set setContact(item) {
        if (item) {
            this.contacts = item.contacts ? item.contacts : [];
        }
    }

    @Output()
    public changeForm: EventEmitter<any> = new EventEmitter();

    formEmail: FormGroup;

    formPhones: FormGroup;

    formAddress: FormGroup;

    contacts = [];

    selectedTab = 'email';

    index;

    amountEmail = 0;

    amountPhones = 0;

    amountAddresses = 0;

    emailTypes = [
        {value: 'MAIN', label: 'contact-info.tag.main'},
        {value: 'RESIDENTIAL', label: 'contact-info.tag.residential'},
        {value: 'COMMERCIAL', label: 'contact-info.tag.commercial'},
        {value: 'OTHER', label: 'contact-info.tag.other'}
    ];

    phoneTypes = [
        {value: 'MAIN', label: 'contact-info.tag.main'},
        {value: 'RESIDENTIAL', label: 'contact-info.tag.residential'},
        {value: 'COMMERCIAL', label: 'contact-info.tag.commercial'},
        {value: 'OTHER', label: 'contact-info.tag.other'},
        {value: 'MOBILE', label: 'contact-info.tag.mobile'}
    ];

    domains = [
        {value: 'NATIONAL', label: 'common.address.national'},
        {value: 'INTERNATIONAL', label: 'common.address.international'}
    ];

    selectedIndex = null;

    zipMask;

    constructor(
        private formBuilder: FormBuilder
    ) {
        super();
    }

    ngOnInit() {
        super.ngOnInit();
        this.resetForms();
        this.setMask();
    }

    resetForms() {
        this.formEmail = this.getFormEmail(null);
        this.formPhones = this.getFormPhones(null);
        this.formAddress = this.getFormAddress(null);
        this.selectedIndex = null;
        this.countContactQuantitiesByType();
    }

    getFormEmail(contact) {
        return this.formBuilder.group({
            id: new FormControl(contact ? contact.id : undefined, []),
            contactInfoType: new FormControl('EMAIL'),
            email: new FormControl(contact ? contact.email : undefined,
                [Validators.required, Validators.maxLength(254),
                    CustomValidators.customEmailValidator,
                    CustomValidators.noWhitespaceValidator]),
            emailTag: new FormControl(contact ? contact.emailTag : undefined, [Validators.required])
        });
    }

    getFormPhones(contact) {
        return this.formBuilder.group({
            id: new FormControl(contact ? contact.id : undefined, []),
            contactInfoType: new FormControl('PHONE'),
            phoneCountryCode: new FormControl(contact ? contact.phoneCountryCode : '+55', [CustomValidators.countryCodePhoneValidator]),
            phone: new FormControl(contact ? contact.phone : undefined,
                [Validators.maxLength(20), CustomValidators.noWhitespaceValidator, CustomValidators.phoneValidator]),
            phoneTag: new FormControl(contact ? contact.phoneTag : undefined, [])
        });
    }

    getFormAddress(contact) {
        return this.formBuilder.group({
            id: new FormControl(contact ? contact.id : undefined, []),
            contactInfoType: new FormControl('ADDRESS'),
            addressStreet: new FormControl(contact ? contact.addressStreet : undefined, [Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            addressComplement: new FormControl(contact ? contact.addressComplement : undefined,
                [Validators.maxLength(100), CustomValidators.noWhitespaceValidator]),
            addressNeighborhood: new FormControl(contact ? contact.addressNeighborhood : undefined,
                [Validators.maxLength(100), CustomValidators.noWhitespaceValidator]),
            contactDomain: new FormControl(contact ? contact.contactDomain : 'NATIONAL',
                []),
            addressZipCode: new FormControl(contact ? contact.addressZipCode : undefined,
                [Validators.maxLength(20), CustomValidators.noWhitespaceValidator, CustomValidators.zipCodeValidator]),
            addressCity: new FormControl(contact ? contact.addressCity : undefined, [Validators.maxLength(100), CustomValidators.noWhitespaceValidator]),
            addressNumber: new FormControl(contact ? contact.addressNumber : undefined,
                [Validators.maxLength(255)]),
            addressState: new FormControl(contact ? contact.addressState : undefined,
                [CustomValidators.noWhitespaceValidator, Validators.maxLength(255)]),
            addressCountry: new FormControl(contact ? contact.addressCountry : undefined,
                [CustomValidators.noWhitespaceValidator, Validators.maxLength(255)])
        });
    }

    addContact(value) {
        if (this.selectedIndex !== null) {
            this.contacts.splice(this.selectedIndex, 1);
        }
        this.contacts.push(value);
        this.resetForms();
        this.changeForm.emit(this.contacts);
    }

    setRemoveIndex(index) {
        this.index = index;
    }

    deleteContact() {
        this.contacts.splice(this.index, 1);
        this.countContactQuantitiesByType();
        this.changeForm.emit(this.contacts);
    }

    resetContactCount() {
        this.amountEmail = 0;
        this.amountPhones = 0;
        this.amountAddresses = 0;
    }

    countContactQuantitiesByType() {
        this.resetContactCount();
        this.contacts.forEach(contact => {
            switch (contact.contactInfoType) {
                case 'EMAIL':
                    this.amountEmail++;
                    break;
                case 'PHONE':
                    this.amountPhones++;
                    break;
                case 'ADDRESS':
                    this.amountAddresses++;
                    break;
            }
        });
    }

    editContactByType(contact, index) {
        this.selectedIndex = index;
        switch (contact.contactInfoType) {
            case 'EMAIL':
                this.formEmail = this.getFormEmail(contact);
                break;
            case 'PHONE':
                this.formPhones = this.getFormPhones(contact);
                break;
            case 'ADDRESS':
                this.formAddress = this.getFormAddress(contact);
                break;
        }
    }

    setMask() {
        if (this.formAddress.get('contactDomain').value === 'NATIONAL') {
            this.zipMask = '00000-000';
        } else {
            this.zipMask = null;
        }
    }

    checkPhoneIsValid() {
        if ((this.formPhones.get('phoneCountryCode').value)
            || (this.formPhones.get('phone').value)
            || (this.formPhones.get('phoneTag').value)
        ) {
            return true;
        }
        return false;
    }

    checkAddressIsValid() {
        if (this.formAddress.get('addressStreet').value
            || this.formAddress.get('addressComplement').value
            || this.formAddress.get('addressNeighborhood').value
            || this.formAddress.get('contactDomain').value
            || this.formAddress.get('addressZipCode').value
            || this.formAddress.get('addressCity').value
            || this.formAddress.get('addressNumber').value
            || this.formAddress.get('addressState').value
            || this.formAddress.get('addressCountry').value
        ) {
            return true;
        }
        return false;
    }

    checkIsValid(contactInfoType) {
        if (contactInfoType === 'PHONE') {
            return this.checkPhoneIsValid();
        } else if (contactInfoType === 'ADDRESS') {
            return this.checkAddressIsValid();
        }
        return false;
    }

    checkEmailIsValid() {
        if (this.contacts.find(contact => contact.emailTag === 'MAIN')) {
            return true;
        }
        return false;
    }

}
