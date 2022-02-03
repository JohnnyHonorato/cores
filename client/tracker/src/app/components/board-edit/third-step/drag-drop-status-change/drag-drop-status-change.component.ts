import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BaseComponent} from 'common';

@Component({
    selector: 'tracker-drag-drop-status-changes',
    templateUrl: './drag-drop-status-change.component.html',
    styleUrls: ['./drag-drop-status-change.component.scss']
})
export class DragDropStatusChangeComponent extends BaseComponent implements OnInit {

    public list: any[];

    @Input() set setList(list) {
        this.list = list.filter((item) => {
            return !item.deleted;
        });
        this.statusListTemp = this.list.slice();
    }

    @Input()
    public control: string;

    @Input()
    public showType: boolean;

    @Output()
    updateList: EventEmitter<object> = new EventEmitter();

    @Output()
    closeChangePositionEvent: EventEmitter<object> = new EventEmitter();

    public statusListTemp = [];

    constructor() {
        super();
    }

    ngOnInit(): void {}

    updateStatusList(status) {
        this.list = status.list;
        this.list.forEach((att => {
            this.setListItemPosition(this.list, att);
        }));
    }

    setListItemPosition(list: any[], item) {
        const index = list.indexOf(item);
        item.position = index + 1;
    }

    saveStatusChanges() {
        this.updateList.emit(this.list);
    }

    closeChangePosition() {
        this.updateList.emit(this.statusListTemp);
    }
}
