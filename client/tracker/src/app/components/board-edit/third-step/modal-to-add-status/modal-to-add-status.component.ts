import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {BaseComponent, CustomValidators} from 'common';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
    selector: 'tracker-modal-to-add-status',
    templateUrl: './modal-to-add-status.component.html',
    styleUrls: ['./modal-to-add-status.component.scss']
})
export class ModalToAddStatusComponent extends BaseComponent implements OnInit {

    @Input() set setStatus(status) {
        this.form = this.getFormControls(status ? status : null);
    }

    @Output() cancel: EventEmitter<any> = new EventEmitter<any>();

    @Output() submitStatus: EventEmitter<any> = new EventEmitter<any>();

    public form: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        public editStatusModal: BsModalRef
    ) {
        super();
    }

    ngOnInit(): void {}

    getFormControls(status) {
        return this.formBuilder.group({
            id: new FormControl(status ? status.id : undefined, []),
            name: new FormControl(status ? status.name : undefined,
                [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            position: new FormControl(status ? status.position : undefined),
            deleted: new FormControl(status ? status.deleted : undefined),
        });
    }

    close() {
        this.cancel.emit();
    }

    saveOrEdit() {
        this.submitStatus.emit(this.form.value);
    }


}
