import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IndexedDbDraftModalType} from '@shared/constants/constants';

@Component({
  selector: 'tracker-draft-modal',
  templateUrl: './draft-modal.component.html',
  styleUrls: ['./draft-modal.component.scss']
})
export class DraftModalComponent implements OnInit {

    @Output()
    private ok: EventEmitter<string> = new EventEmitter();

    @Output()
    private no: EventEmitter<string> = new EventEmitter();

    @Output()
    private closeModal: EventEmitter<any> = new EventEmitter();

    @Input() type: any;

    retrieveDraftDataType = IndexedDbDraftModalType.RETRIEVE_DATA;

    discardDraftDataType = IndexedDbDraftModalType.DISCARD_DATA;

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

    onClose() {
        this.closeModal.emit();
    }
}

