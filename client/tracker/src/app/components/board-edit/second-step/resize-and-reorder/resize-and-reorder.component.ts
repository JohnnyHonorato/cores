import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'tracker-resize-and-reorder',
    templateUrl: './resize-and-reorder.component.html',
    styleUrls: ['./resize-and-reorder.component.scss']
})
export class ResizeAndReorderComponent implements OnInit {

    public list: any[];

    public unmodifiedList = [];

    @Input() set setList(list) {
        this.list = list.filter((item) => {
            return !item.deleted;
        });
        this.unmodifiedList = this.list.slice();
    }

    @Output()
    update: EventEmitter<object> = new EventEmitter();

    @Output() customAttList;

    constructor() {
    }

    ngOnInit(): void {
        this.customAttList = this.unmodifiedList;
    }

    closeChange() {
        this.update.emit(this.unmodifiedList);
    }

    saveChanges() {
        this.update.emit(this.list);
    }

    updateItemList(gridUpdated) {
        this.list = gridUpdated;
    }

}
