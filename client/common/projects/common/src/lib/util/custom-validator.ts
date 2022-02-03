import {AbstractControl, FormControl, ValidatorFn, FormGroup, ValidationErrors, AsyncValidatorFn} from '@angular/forms';
import * as moment from 'moment';

declare var $: any;

const lang = localStorage.getItem('lang');

export class WarnControl extends FormControl { warnings: {[key: string]: boolean}; }

export class CustomValidators {

    static noWhitespaceValidator(control: FormControl) {
        if (control.value === null || control.value === '') {
            return null;
        }
        const isWhitespace = (control.value || '').trim().length === 0;
        return isWhitespace ? {whitespace: true} : null;
    }

    static customEmailValidator(control: FormControl) {
        if (control.value === null || control.value === '') {
            return null;
        }
        const pattern = new RegExp('^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$');
        return !pattern.test(control.value) ? {email: true} : null;
    }

    static passwordAndConfirmationValidator(control: FormControl) {
        const form = control.parent;

        if (form && (form.get('password').value !== form.get('passwordConfirmation').value)) {
            return {passwordNotSame: true};
        }

        return null;
    }

    static maxNumberLength(max: number, required: boolean): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            if (control.value == null) {
                return required ? {maxNumber: true} : null;
            } else {
                const numberStr: string = control.value.toString().replace(/[\.,]/g, '');
                return numberStr.length > max ? {maxNumber: max} : null;
            }
        };
    }

    static dateValidator(inputId: string): ValidatorFn {
        return (): { [key: string]: any } | null => {
            const value = this.checkDateStringContent($(inputId).val());
            if (!value) {
                return null;
            }
            return (lang === 'pt' && moment(value, ['D/M/YYYY'], true).isValid()) ||
            (lang === 'en' && moment(value, ['M/D/YYYY'], true).isValid()) ? null : {invalidDate: true};
        };
    }

    static matchPassword(controlName: string, matchingControlName: string) {
        return (formGroup: FormGroup) => {
            const controls = formGroup.controls;
            if (!controls[controlName] || !controls[matchingControlName]) {
                return;
            }
            const control = controls[controlName];
            const matchingControl = controls[matchingControlName];

            if (matchingControl.errors && !matchingControl.errors.passwordNotSame) {
                // return if another validator has already found an error on the matchingControl
                return;
            }

            // set error on matchingControl if validation fails
            if (control.value !== matchingControl.value) {
                matchingControl.setErrors({passwordNotSame: true});
            } else {
                matchingControl.setErrors(null);
            }
        };
    }

    static listLength(min: number = null, max: number = null, deletedAttribute: string = null) {
        return (control: AbstractControl) => {
            let list = control.value as Array<any> || [];
            if (deletedAttribute) {
                list = list.filter(item => !item[deletedAttribute]);
            }
            const errors: ValidationErrors = {};
            if (min && list.length < min) {
                errors.minListLength = {requiredLength: min, actualLength: list.length};
            }
            if (max && list.length > max) {
                errors.maxListLength = {requiredLength: max, actualLength: list.length};
            }
            if (Object.values(errors).some(e => e)) {
                return errors;
            }
            return null;
        };
    }

    static checkIfExistsSomeNullValue(minValueControlName: string, maxValueControlName: string) {
        return (formGroup: FormGroup) => {
            const minComponent = formGroup.controls[minValueControlName];
            const maxComponent = formGroup.controls[maxValueControlName];

            if (minComponent.errors && !maxComponent.errors) {
                return null;
            }

            if ((minComponent.value == null && maxComponent.value != null) || (minComponent.value != null && maxComponent.value == null)) {
                maxComponent.setErrors({invalidValue: true});
                return {invalidValue: true};
            } else {
                maxComponent.setErrors(null);
                return null;
            }
        };
    }

    static checkIfValueNotRequiredInRange(valueControlName: string, minControlName: string, maxControlName: string) {
        return (formGroup: FormGroup) => {
            const minComponent = formGroup.controls[minControlName];
            const maxComponent = formGroup.controls[maxControlName];
            const valueComponent = formGroup.controls[valueControlName];

            if (this.isEmpty(valueComponent.value) && this.isEmpty(minComponent.value) && this.isEmpty(maxComponent.value)) {
                minComponent.setErrors(null);
                return null;
            }

            return this.testIfValuesInRange(valueComponent, minComponent, maxComponent);
        };
    }

    static checkIfValueInRange(valueControlName: string, minControlName: string, maxControlName: string) {
        return (formGroup: FormGroup) => {
            const minComponent = formGroup.controls[minControlName];
            const maxComponent = formGroup.controls[maxControlName];
            const valueComponent = formGroup.controls[valueControlName];

            if (valueComponent.value === null ||
                parseFloat(valueComponent.value) < 0 ||
                parseFloat(minComponent.value) < 0 ||
                parseFloat(maxComponent.value) < 0) {
                return;
            }

            return this.testIfValuesInRange(valueComponent, minComponent, maxComponent);
        };
    }

    static checkIfDateIsBiggerThen(dateOneName: string, dateTwoName: string) {
        return (formGroup: FormGroup) => {
            const date1 = formGroup.controls[dateOneName];
            const date2 = formGroup.controls[dateTwoName];

            if (date1.value > date2.value) {
                date1.setErrors(null);
                return null;
            }

            date1.setErrors({dateNotBigger: true});
            return {dateNotBigger: true};
        };
    }

    private static testIfValuesInRange(valueComponent, minComponent, maxComponent) {

        if (!this.isEmpty(valueComponent.value) && this.isEmpty(minComponent.value) && this.isEmpty(maxComponent.value)) {
            valueComponent.setErrors(null);
            return null;
        }

        if (!this.isEmpty(valueComponent.value) && !this.isEmpty(minComponent.value) && !this.isEmpty(maxComponent.value)) {
            if (parseFloat(minComponent.value) <= parseFloat(valueComponent.value)
                && parseFloat(valueComponent.value) <= parseFloat(maxComponent.value)) {
                valueComponent.setErrors(null);
                minComponent.setErrors(null);
                maxComponent.setErrors(null);
                return null;
            }
        }
        if (this.isEmpty(valueComponent.value)) {
            valueComponent.setErrors({required: true});
            return {required: true};
        }

        if (parseFloat(minComponent.value) > parseFloat(maxComponent.value)) {
            minComponent.setErrors({minGreaterThanMax: true});
            return {minGreaterThanMax: true};
        }

        valueComponent.setErrors({idealNotBetweenMinMax: true});
        return {idealNotBetweenMinMax: true};
    }

    private static isEmpty(value) {
        return (value === null || value === undefined || value.length === 0);
    }

    static cnpjValidator(control: FormControl) {
        if (control.value == null) {
            return {cnpj: true};
        } else {
            const cnpj: string = control.value.toString().replace(/[\.|\/|\- ]/g, '');

            if (cnpj.length !== 14) {
                return {cnpj: true};
            }
            if (cnpj === '00000000000000' ||
                cnpj === '11111111111111' ||
                cnpj === '22222222222222' ||
                cnpj === '33333333333333' ||
                cnpj === '44444444444444' ||
                cnpj === '55555555555555' ||
                cnpj === '66666666666666' ||
                cnpj === '77777777777777' ||
                cnpj === '88888888888888' ||
                cnpj === '99999999999999') {

                return {cnpj: true};
            }

            let listLength = cnpj.length - 2;
            let numbers = cnpj.substring(0, listLength);
            const digits = cnpj.substring(listLength);

            let sum: any = 0;
            let pattern = listLength - 7;
            for (let i = listLength; i >= 1; i--) {
                sum += parseInt(numbers.charAt(listLength - i), 10) * pattern--;
                if (pattern < 2) {
                    pattern = 9;
                }
            }
            let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
            if (result !== parseInt(digits.charAt(0), 10)) {
                return {cnpj: true};
            }

            listLength = listLength + 1;
            numbers = cnpj.substring(0, listLength);
            sum = 0;
            pattern = listLength - 7;
            for (let i = listLength; i >= 1; i--) {
                sum += parseInt(numbers.charAt(listLength - i), 10) * pattern--;
                if (pattern < 2) {
                    pattern = 9;
                }
            }
            result = sum % 11 < 2 ? 0 : 11 - sum % 11;
            if (result !== parseInt(digits.charAt(1), 10)) {
                return {cnpj: true};
            }

            return null;
        }
    }

    static cpfValidator(control: FormControl) {
        if (control.value == null || control.value === '') {
            return null;
        } else {
            const cpf: string = control.value.toString().replace(/[\.|\/|\- ]/g, '');

            if (cpf.length !== 11) {
                return {cpf: true};
            }
            if (cpf === '00000000000' ||
                cpf === '11111111111' ||
                cpf === '22222222222' ||
                cpf === '33333333333' ||
                cpf === '44444444444' ||
                cpf === '55555555555' ||
                cpf === '66666666666' ||
                cpf === '77777777777' ||
                cpf === '88888888888' ||
                cpf === '99999999999') {

                return {cpf: true};
            }

            let listLength = cpf.length - 2;
            let numbers = cpf.substring(0, listLength);
            const digits = cpf.substring(listLength);
            let sum: any = 0;
            let pattern = 10;
            for (let i = listLength; i >= 1; i--) {
                sum += parseInt(numbers.charAt(listLength - i), 10) * pattern--;
            }
            let result = ((sum * 10) % 11) === 10 ? 0 : (sum * 10) % 11;
            if (result !== parseInt(digits.charAt(0), 10)) {
                return {cpf: true};
            }

            listLength = listLength + 1;
            numbers = cpf.substring(0, listLength);
            sum = 0;
            pattern = 11;
            for (let i = listLength; i >= 1; i--) {
                sum += parseInt(numbers.charAt(listLength - i), 10) * pattern--;
            }
            result = ((sum * 10) % 11) === 10 ? 0 : (sum * 10) % 11;
            if (result !== parseInt(digits.charAt(1), 10)) {
                return {cpf: true};
            }

            return null;
        }
    }

    static zipCodeValidator(control: FormControl) {
        if (control.value == null || control.value === '') {
            return null;
        } else {
            const cnpj: string = control.value.toString().replace(/[\- ]/g, '');
            return cnpj.length !== 8 ? {zipcode: true} : null;
        }
    }

    static checkDateStringContent(date: string) {
        if (date) {
            const str = date.split('/');
            if (str[0] && str[0].length > 2 && str[1]) {
                date = str[0].slice(0, 2) + '/' + str[0][2] + str[1][0] + '/' + str[1][1] + str[2];
            } else if (str[1] && str[1].length > 2) {
                date = str[0] + '/' + str[1].slice(0, 2) + '/' + str[1][2] + str[2];
            }

            return date;
        }
    }

    static phoneValidator(control: FormControl) {
        if (control.value == null || control.value === '') {
            return null;
        } else {
            const phone: string = control.value.toString();
            const isValid = /^[0-9- ]*$/.test(phone);
            return !isValid ? {pattern: true} : null;
        }
    }

    static countryCodePhoneValidator(control: FormControl) {
        if (control.value == null || control.value === '') {
            return null;
        } else {
            const code: string = control.value.toString();
            const isValid = /^[+]\d{1,3}$/.test(code);
            return !isValid ? {countryCodePhone: true} : null;
        }
    }

    static dateLessThan(firstDateField: string, secondDateField: string): ValidatorFn {
        return (form: FormGroup): { [key: string]: boolean } | null => {
            const firstDateControl = form.get(firstDateField);
            const secondDateControl = form.get(secondDateField);
            if (firstDateControl.dirty || secondDateControl.dirty) {
                const firstDateValue = firstDateControl.value;
                const secondDateValue = secondDateControl.value;

                if (!firstDateValue || !secondDateValue) {
                    return null;
                }

                const firstDate = new Date(firstDateValue);
                const secondDate = new Date(secondDateValue);

                if (firstDate.getTime() > secondDate.getTime()) {
                    const err = {dateNotBigger: true};
                    firstDateControl.setErrors(err);
                    firstDateControl.markAsTouched();
                    return err;
                } else {
                    const dateLessError = form.get(firstDateField).hasError('dateNotBigger');
                    if (dateLessError) {
                        delete form.get(firstDateField).errors.dateNotBigger;
                        form.get(firstDateField).updateValueAndValidity();
                    }
                }
            }
        };
    }

    static checkIfMinValueIsBiggerThanMaxValue(): ValidatorFn {
        return (formGroup: FormGroup): ValidationErrors => {
            const minValue = formGroup.controls.minValue.value;
            const maxValue = formGroup.controls.maxValue.value;
            if (!minValue || !maxValue) {
                return;
            } else if (+minValue > +maxValue) {
                formGroup.controls.minValue.setErrors({minValueBiggerThanMaxValue: true});
                return {minValueBiggerThanMaxValue: true};
            }

            formGroup.controls.minValue.setErrors(null);
            return null;
        };
    }

    static deletedWarning(deletedId: any): ValidatorFn {
        return (control: WarnControl) => {
            if (control.value) {
                control.warnings = control.value.deleted ? {deleted: deletedId} : null;
            }
            return null;
        };
    }

}

