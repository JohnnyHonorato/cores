import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

declare var $: any;

@Component({
    selector: 'tracker-drag-drop-items',
    templateUrl: './drag-drop-items.component.html',
    styleUrls: ['./drag-drop-items.component.scss']
})
export class DragDropItemsComponent implements OnInit {

    @Input()
    public list: any[];

    @Input()
    public control: string;

    @Input()
    public tracker: any;

    @Input()
    public showType: boolean;

    @Output()
    updateList: EventEmitter<object> = new EventEmitter();

    @Output()
    updateItem: EventEmitter<object> = new EventEmitter();

    @Output()
    update: EventEmitter<object> = new EventEmitter();

    @Output()
    refresh: EventEmitter<string> = new EventEmitter();

    @Output()
    itemRemoved: EventEmitter<object> = new EventEmitter();

    public editCustomAttributeMode = false;
    public selectedAttribute: any;
    public item: any;
    public index: number;
    public organization = 'ORGANIZATION';
    public isRelatedAttribute = false;
    public items = [];

    constructor() {
        this.items = this.list;
    }

    ngOnInit(): void {
    }

    listItems(): void {
        this.refresh.emit();
    }

    async drop(event: CdkDragDrop<any[]>) {
        await moveItemInArray(this.list, event.previousIndex, event.currentIndex);
        this.updateList.emit({ list: this.list, control: this.control });
    }

    editStatus(status, index) {
        $('#' + index).prop('disabled', true);
        this.updateItem.emit({ status, index });
    }

    openCustomAttributeModal(item) {
        this.editCustomAttributeMode = true;
        this.selectedAttribute = item ? item : undefined;
    }

    addCustomAttribute(attribute) {
        attribute.position = this.selectedAttribute.position;
        attribute.trackerModel = this.selectedAttribute.trackerModel;
        this.updateItem.emit({ attribute, selectedAttribute: this.selectedAttribute });
        this.closeModal();
    }

    closeModal() {
        this.editCustomAttributeMode = false;
        this.selectedAttribute = null;
    }

    removeFromList() {
        this.update.emit({ index: this.index, control: this.control });
        this.closeModalDeleteRelationship();
    }

    getNameToDisplay(item: any): string {
        if (this.control === 'status' || this.control === 'orderStatus') {
            return item.name;
        } else if (this.control === 'attributes') {
            return item.title;
        }
    }

    setRemoveItemAndIndex(item, index: number) {
        this.isRelatedAttribute = null;
        this.item = item;
        this.index = index;

        this.OpenModalDeleteRelationshipOrAttributes(item);
    }

    getTypeToDisplayFormatted(item: string) {
        return `tracker-manager.attribute.type.${item.replace('_', '').toLowerCase()}`;
    }

    closeModalDeleteRelationship() {
        $('#confirmation-delete-relationship').modal('hide');
    }

    private OpenModalDeleteRelationshipOrAttributes(item) {
        this.list.forEach((att => {
            if (!att.deleted && att.relatedAttribute && att.relatedAttribute.title === item.title) {
                this.isRelatedAttribute = true;
            }
        }));

        if (item.type === this.organization && this.isRelatedAttribute) {
            $('#confirmation-delete-relationship').modal('toggle');
        } else {
            $('#modal-delete-attributes').modal('toggle');
        }
    }

    getItem(): any {
        return this.item?.title;
    }
}
