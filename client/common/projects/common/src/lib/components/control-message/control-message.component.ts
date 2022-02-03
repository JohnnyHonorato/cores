import {Component, Input} from '@angular/core';
import {FormControl} from '@angular/forms';
import {WarnControl} from '../../util/custom-validator';

@Component({
    selector: 'common-control-message',
    templateUrl: './control-message.component.html',
    styleUrls: ['./control-message.component.scss']
})
export class ControlMessagesComponent {

    static MESSAGES = {
        required: 'system.form.validation.required',
        email: 'system.form.validation.email',
        max: 'system.form.validation.max-value',
        min: 'system.form.validation.min-value',
        maxlength: 'system.form.validation.max-size',
        minlength: 'system.form.validation.min-size',
        maxListLength: 'system.form.validation.max-list-length',
        minListLength: 'system.form.validation.min-list-length',
        dateNotBigger: 'system.form.validation.date-not-bigger',
        idealNotBetweenMinMax: 'system.form.validation.ideal-not-between-min-max',
        invalidPassword: 'system.form.validation.password.invalid',
        invalidDate: 'system.form.validation.invalid-date',
        passwordNotSame: 'system.form.validation.password.not-same',
        onlyNumbers: 'system.form.validation.only-numbers',
        minGreaterThanMax: 'system.form.validation.min-greater-than-max',
        whitespace: 'system.form.validation.invalid',
        pattern: 'system.form.validation.pattern',
        cnpj: 'system.form.validation.cnpj',
        zipcode: 'system.form.validation.zipcode',
        cpf: 'system.form.validation.cpf',
        matDatepickerMin: 'system.form.validation.min-date',
        matDatepickerMax: 'system.form.validation.max-date',
        emailMain: 'system.form.validation.one-main-email',
        phoneMain: 'system.form.validation.one-main-phone',
        countryCodePhone: 'system.form.validation.code-phone-invalid-pattern',
        minValueBiggerThanMaxValue: 'system.form.validation.min-value-bigger-than-max-value',
        deleted: 'system.form.warning.deleted'
    };


    @Input() control: FormControl;

    constructor() {
    }

    get errorMessage() {
        if (this.control) {
            for (const propertyName in this.control.errors) {
                if (
                    this.control.errors.hasOwnProperty(propertyName) &&
                    this.control.touched
                ) {
                    return ControlMessagesComponent.getValidatorMessage(
                        propertyName,
                        this.control.errors[propertyName]
                    );
                }
            }
        }
        return null;
    }

    get warningMessage() {
        if (this.control && this.control instanceof WarnControl) {
            for (const propertyName in this.control.warnings) {
                if (this.control.warnings.hasOwnProperty(propertyName)) {
                    return ControlMessagesComponent.getValidatorMessage(
                        propertyName,
                        this.control.warnings[propertyName]
                    );
                }
            }
        }
        return null;
    }

    static getValidatorMessage(validatorName: string, validatorValue?: any) {
        return {
            message: ControlMessagesComponent.MESSAGES[validatorName],
            value: ControlMessagesComponent.extractValidatorValue(
                validatorName,
                validatorValue
            )
        };
    }

    static extractValidatorValue(validatorName: string, validatorValue?: any) {
        switch (validatorName) {
            case 'max':
                return validatorValue.max;

            case 'min':
                return validatorValue.min;

            case 'maxlength':
            case 'minlength':
            case 'maxListLength':
            case 'minListLength':
                return validatorValue.requiredLength;

            case 'invalidPassword':
                return 'invalidPassword';

            case 'passwordNotSame':
                return 'passwordNotSame';
        }

        return validatorValue;
    }

}
