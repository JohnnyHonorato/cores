import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FiltersListComponent} from './filters-list/filters-list.component';
import {FilterEditComponent} from './filter-edit/filter-edit.component';

@Component({
    selector: 'tracker-filters',
    templateUrl: './filters.component.html',
    styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

    @Input() openFilter;

    @Input() set filter(value) {
        this.selectedFilter = value;
    }

    @Output() activeFilter = new EventEmitter<any>();

    @Output() clearFilters = new EventEmitter<any>();

    @Output() removeItemFilter: EventEmitter<any> = new EventEmitter();

    @ViewChild(FiltersListComponent, {static: false})
    private filtersListComponent: FiltersListComponent;

    @ViewChild(FilterEditComponent, {static: false})
    private filterEditComponent: FilterEditComponent;

    trackerModelId;

    selectedFilter = null;

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.trackerModelId = params.id;
        });
    }

    selectFilter(value) {
        this.openFilter = true;
        this.selectedFilter = value;
        this.activeFilter.emit(value);
    }

    saveFilter(event) {
        if (this.filtersListComponent) {
            this.filtersListComponent.listItems();
            this.filtersListComponent.appliedFilter = event;
        }
    }

    cleanFilter() {
        if (this.filtersListComponent) {
            this.filtersListComponent.appliedFilter = null;
        }
        this.selectedFilter = null;
        this.clearFilters.emit();
    }

    removeAppliedFilter() {
        if (this.filterEditComponent) {
            this.filterEditComponent.clean();
            this.cleanFilter();
        }
    }

    removeFilterItem(filterItem) {
        this.removeItemFilter.emit(filterItem);
    }
}
