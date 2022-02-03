import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BaseComponent, CustomValidators} from 'common';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'tracker-first-step',
    templateUrl: './first-step.component.html',
    styleUrls: ['./first-step.component.scss']
})
export class FirstStepComponent extends BaseComponent implements OnInit {

    nameObject;

    descriptionObject;

    form: FormGroup;

    @Input() nextStep = false;

    @Input()
    set setObjectValue(object) {
        if (object.description != null) {
            this.descriptionObject = object.description;
        }
        if (object.name != null) {
            this.nameObject = object.name;
        }
        this.form = this.getFormControls();
    }

    @Output() formIsValid: EventEmitter<any> = new EventEmitter<any>();

    @Output() backToList: EventEmitter<any> = new EventEmitter<any>();


    constructor(
        private formBuilder: FormBuilder,
    ) {
        super();
    }

    ngOnInit() {
        this.form = this.getFormControls();
        this.validForm();
    }

    getFormControls() {
        return this.formBuilder.group({
            name: new FormControl(this.nameObject ? this.nameObject : undefined,
                [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            description: new FormControl(this.descriptionObject ? this.descriptionObject :
                undefined, [Validators.maxLength(5000)])
        });
    }

    validForm() {
        if (this.form.valid) {
            this.formIsValid.emit({form: this.form, isValid: true});
        } else {
            this.formIsValid.emit({form: this.form, isValid: false});
        }
    }

}
