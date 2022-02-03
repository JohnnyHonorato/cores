import {Validators} from '@angular/forms';
import {CustomValidators} from 'common';

export function getValidators(trackerAttribute: any, attributeValue: any) {
    const validators = [];
    if (trackerAttribute.required) {
        validators.push(Validators.required);
        if (trackerAttribute.type === 'STRING') {
            validators.push(CustomValidators.noWhitespaceValidator);
        }
    }
    if (trackerAttribute.minValue !== null && trackerAttribute.minValue !== undefined) {
        validators.push(Validators.min(trackerAttribute.minValue));
    }
    if (trackerAttribute.maxValue !== null && trackerAttribute.minValue !== undefined) {
        validators.push(Validators.max(trackerAttribute.maxValue));
    }
    if (trackerAttribute.maxLength !== null && trackerAttribute.maxLength !== undefined) {
        validators.push(Validators.maxLength(trackerAttribute.maxLength));
    }
    if (attributeValue?.value?.deleted) {
        validators.push(CustomValidators.deletedWarning(attributeValue.value.id));
    }

    return validators;
}
