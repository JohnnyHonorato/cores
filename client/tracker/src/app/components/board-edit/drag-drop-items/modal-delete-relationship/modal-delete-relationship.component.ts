import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'tracker-modal-delete-relationship',
    templateUrl: './modal-delete-relationship.component.html',
    styleUrls: ['./modal-delete-relationship.component.scss']
})
export class ModalDeleteRelationshipComponent implements OnInit {

    @Output()
    private ok: EventEmitter<string> = new EventEmitter();
    private no: EventEmitter<string> = new EventEmitter();

    @Input()
    set setValue(value) {
        this.item = value;
    }

    public item: any;

    constructor() {
    }

    ngOnInit(): void {
    }

    confirmation() {
        this.ok.emit();
    }

    cancel() {
        this.no.emit();
    }
}
