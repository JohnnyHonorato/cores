import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BsModalService, BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'tracker-checklist-item',
  templateUrl: './checklist-item.component.html',
  styleUrls: ['./checklist-item.component.scss']
})
export class ChecklistItemComponent {

    @Input() item: any;

    @Input() itemIndex: number;

    @Input() isView: boolean;

    @Input() checklistIndex: number;

    @Output() checklistItemValueChanged: EventEmitter<any> = new EventEmitter<any>();

    @Output() updateItem: EventEmitter<any> = new EventEmitter<any>();

    @Output() deleteChecklistItem: EventEmitter<any> = new EventEmitter<any>();

    modalRef: BsModalRef;

    constructor(private modalService: BsModalService) {
    }

    itemValueChanged(): void {
        this.checklistItemValueChanged.emit(this.itemIndex);
    }

    update(): void {
        this.updateItem.emit({name: this.item.name, itemIndex: this.itemIndex});
    }

    delete(): void {
        this.deleteChecklistItem.emit(this.itemIndex);
    }

    closeModal() {
        this.modalRef.hide();
    }

}
