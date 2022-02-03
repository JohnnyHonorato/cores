import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BaseListComponent} from 'common';
import {TrackerModelURL} from '@shared/util/url.domain';

@Component({
    selector: 'tracker-filters-list',
    templateUrl: './filters-list.component.html',
    styleUrls: ['./filters-list.component.scss']
})
export class FiltersListComponent extends BaseListComponent implements OnInit {

    @Input() trackerModelId;

    @Output() selectedFilter = new EventEmitter<any>();

    @Output() deleteAppliedFilter = new EventEmitter<any>();

    appliedFilter: any;

    filters: any[];

    numberFilters = 0;

    constructor() {
        super();
        this.appliedFilter = null;
    }

    ngOnInit() {
        super.ngOnInit();
    }

    getRouterURL(): string {
        return 'tracker';
    }

    getServiceURL(): string {
        return `${TrackerModelURL.BASE}/${this.trackerModelId}/filters`;
    }

    onSelectFilter(value) {
        this.appliedFilter = value;
        this.selectedFilter.emit(value);
    }

    setFavorite(filter) {
        const isFavorite = !filter.isFavourite;
        this.service.patch(`${this.getServiceURL()}/${filter.id}/favourite/${isFavorite}`, null).subscribe(result => {
            filter.isFavourite = !filter.isFavourite;
        }, error => {
            this.notificationService.error(error.error.message);
        });
    }

    onScroll() {
        if (this.currentPage < this.totalPages) {
            this.currentPage = this.currentPage + 1;
            this.onChangePaginator(this.currentPage);
        }
    }

    protected postResult(): void {
        this.setNumberFilters();
        if (this.currentPage === 1) {
            this.filters = [...this.items];
        } else {
            this.filters = [...this.filters, ...this.items];
        }
    }

    protected postDelete() {
        this.listItems();
    }

    setNumberFilters() {
        this.service.get(this.getServiceURL() + '/count').subscribe((result: any) => {
            this.numberFilters = parseInt(result, 10);
        });
    }

    remove(id) {
        if (this.appliedFilter?.id === id) {
            this.deleteAppliedFilter.emit();
        }
        super.remove(id);
    }
}
