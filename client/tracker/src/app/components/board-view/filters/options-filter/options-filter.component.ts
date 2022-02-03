import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
    selector: 'tracker-options-filter',
    templateUrl: './options-filter.component.html',
    styleUrls: ['./options-filter.component.scss']
})
export class OptionsFilterComponent implements OnInit {

    @Output() openCloseFilter = new EventEmitter<any>();

    isOpenFilter = false;

    constructor() {
    }

    ngOnInit(): void {
    }

    openFilter() {
        this.isOpenFilter = !this.isOpenFilter;
        this.openCloseFilter.emit(this.isOpenFilter);
    }

}
