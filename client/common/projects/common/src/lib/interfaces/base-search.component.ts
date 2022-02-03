import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {debounceTime, switchMap} from 'rxjs/operators';
import {SearchService} from '../services/search.service';
import {AppInjector} from '../util/app.injector';

@Component({
    template: ''
})
export abstract class BaseSearchComponent implements OnInit {

    /**
     * Url that's gonna be requested
     */
    @Input() serviceUrlSearch: string;

    /**
     * Current page on pagination
     */
    @Input() currentPage: number;
    /**
     * Quantity of items to show at each page
     */
    @Input() pageSize: number;

    /**
     * Column name to use as base to search
     */
    @Input() currentColumn: string;

    /**
     * Current type of sorting
     */
    @Input() currentSort: string;

    /**
     * The time to debounce
     */
    @Input() debounceTime: 250;

    /**
     * To emit the result's returned by the service
     */
    @Output() results: EventEmitter<any> = new EventEmitter();

    /**
     * To emit the current search of the components, this is used by BaseListComponent
     */
    @Output() currentSearch: EventEmitter<any> = new EventEmitter();

    /**
     * To emit the loading status of the components
     */
    @Output() isLoading: EventEmitter<any> = new EventEmitter();

    public filters = [];

    private actualSearch = new FormControl();
    private params = {
        currentPage: 1,
        pageSize: 5,
        search: '',
        sort: {}
    };

    private searchService: SearchService = AppInjector.get(SearchService);

    ngOnInit() {
        this.initActualSearchChangesSubscriber();
    }

    /**
     * Executes the search when the user input's
     * @param searchTerm input value
     */
    executeSearch(searchTerm) {
        this.params = {
            currentPage: this.currentPage ? this.currentPage : 1,
            pageSize: this.pageSize ? this.pageSize : 5,
            search: searchTerm === null ? '' : searchTerm,
            sort: {
                order: this.currentSort ? this.currentSort : '',
                column: this.currentColumn ? this.currentColumn : ''
            }
        };
        this.actualSearch.setValue(searchTerm);
    }

    initActualSearchChangesSubscriber() {
        this.actualSearch.valueChanges
            .pipe(
                debounceTime(this.debounceTime),
                switchMap(() => {
                    this.isLoading.emit(true);
                    return this.searchService.search(this.serviceUrlSearch, this.params, this.filters);
                })
            ).subscribe(result => {
            this.currentSearch.emit(this.actualSearch.value);
            this.isLoading.emit(false);
            this.results.emit(result);
        });
    }
}
