import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from 'common';

@Component({
    selector: 'tracker-popover-checklist',
    templateUrl: './popover-checklist.component.html',
    styleUrls: ['./popover-checklist.component.scss']
})
export class PopoverChecklistComponent implements OnInit {

    @Input() checklistName = '';
    @Input() isPreview: string;

    @Output() checklistAdded: EventEmitter<any> = new EventEmitter<any>();

    form: FormGroup;

    constructor() {}

    ngOnInit(): void {
        this.initForm();
    }

    initForm(): void {
        this.form = new FormGroup(this.getFormControls());
    }

    getFormControls(): {} {
        return {
            name: new FormControl(this.checklistName ? this.checklistName : undefined, [Validators.required,
                Validators.maxLength(255), CustomValidators.noWhitespaceValidator])
        };
    }

    hasError(control: AbstractControl): string {
        return control && control.touched && control.invalid ? 'is-invalid state-invalid' : '';
    }

    emitChecklistName(): void {
        this.checklistAdded.emit(this.form.controls.name.value);
        this.resetForm();
    }

    resetForm(): void {
        if (!this.checklistName) {
            this.form.reset();
        }
    }

}
