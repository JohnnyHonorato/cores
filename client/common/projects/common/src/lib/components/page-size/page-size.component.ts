import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'common-page-size',
    templateUrl: './page-size.component.html',
    styleUrls: ['./page-size.component.scss']
})
export class PageSizeComponent {

    @Output()
    public pageSizeChanged = new EventEmitter<any>();

    @Input()
    public itemsPerPage = [10, 25, 50, 100, 150, 200];

    constructor() {
    }

    setPageSize(value: number) {
        this.pageSizeChanged.emit(value);
    }

}
