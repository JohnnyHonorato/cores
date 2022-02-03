import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {BaseComponent} from 'common';
import MIMETYPES from '@shared/util/mime-types.json';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
    selector: 'tracker-attachment-types',
    templateUrl: './attachment-types.component.html',
    styleUrls: ['./attachment-types.component.scss']
})
export class AttachmentTypesComponent extends BaseComponent implements OnInit {

    @Output() submitAttachmentTypes: EventEmitter<any> = new EventEmitter<any>();

    @Input() public types;

    public typeList = [];
    public form: FormGroup;


    constructor(
        public modalAttachmentTypes: BsModalRef,
        private formBuilder: FormBuilder
    ) {
        super();
    }

    setForm() {
        const typeList = [];
        this.types.split(';').forEach(element => {
            if (element) {
                typeList.push(element.split(':')[0]);
            }
        });
        this.form = this.getFormControls(typeList ? typeList : null);
    }

    getFormControls(types) {
        return this.formBuilder.group({
            types: new FormControl(types ? types : undefined, [])
        });
    }

    ngOnInit(): void {
        this.typeList = Object.keys(MIMETYPES);
        this.setForm();
    }

    saveOrEdit() {
        let attachmentTypes = '';
        this.form.get('types').value.forEach(element => {
            const type = element + ':' + MIMETYPES[element];
            attachmentTypes = attachmentTypes + type + ';';
        });
        this.submitAttachmentTypes.emit(attachmentTypes);
    }

}
