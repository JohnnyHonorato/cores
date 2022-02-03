import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';

@Component({
    selector: 'tracker-modal-confirmation-monetary',
    templateUrl: './modal-confirmation-monetary.component.html',
    styleUrls: ['./modal-confirmation-monetary.component.scss']
})
export class ModalConfirmationMonetaryComponent implements OnInit {

    @Output()
    public closeModal: EventEmitter<boolean> = new EventEmitter();

    constructor() {
    }

    ngOnInit(): void {
    }

    confirmation() {
        this.closeModal.emit(true);
    }

}
