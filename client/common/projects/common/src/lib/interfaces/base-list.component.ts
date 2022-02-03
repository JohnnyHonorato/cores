import {Component, OnInit, ViewChild} from '@angular/core';

import {BaseModelComponent} from './base-model.component';
import {PropertyType} from '../domain/property-type.domain';
import {Comparison} from '../domain/comparison.domain';
import {DeleteConfirmationComponent} from '../components/delete-confirmation/delete-confirmation.component';
import {FIRST_PAGE} from '../util/constants';

/**
 * The 'BaseListComponent' provides the common API for all list components.
 *
 * Service, operations, searches, navigations are all available.
 *
 * All list components MUST extend this class.
 *
 * @extends BaseModelComponent
 */
@Component({
    template: ''
})
export abstract class BaseListComponent extends BaseModelComponent implements OnInit {

    /**
     * Component to confirm the remove operation.
     *
     * Could exist or not. Depends if the template has the <app-delete-confirmation> tag.
     */
    @ViewChild(DeleteConfirmationComponent, {static: false})
    deleteConfirmation: DeleteConfirmationComponent;

    /**
     * Items to list.
     */
    public items: any = [];

    /**
     * ID to be removed.
     */
    protected removeId: any;

    /**
     * Total pages of items.
     */
    public totalPages = 1;

    /**
     * Current page of pagination.
     */
    public currentPage = 1;

    /**
     * Current column of ordering.
     */
    public currentColumn: string;

    /**
     * Current sort of ordering.
     */
    public currentSort: string;

    /**
     * Current text of search.
     */
    public currentSearch: string;

    /**
     * Current number of paginate.
     */
    public currentPageSize = 10;

    /**
     * Current object of filter.
     */
    public currentFilter: Array<any> = [];

    /**
     * Current object of filter.
     */
    public loading = true;

    /**
     * Page size options.
     */
    public pageSizeOptions: number[];

    /**
     * Properties to be included on text search.
     */
    public searchProperties: any[] = [];

    /**
     * Constructor.
     */
    constructor() {
        super();
    }

    /**
     * On Init of the components.
     *
     * List all items by default.
     */
    ngOnInit(): void {
        super.ngOnInit();

        this.initSearch();

        this.listItems();
    }

    /**
     * Initializes the search.
     */
    initSearch(): void {
        this.advancedSearch();
    }

    /**
     * If the components will use a advanced search, it must override this method to
     * include the properties to be use in the advanced search.
     *
     * It should call 'addSearch' method only.
     */
    protected advancedSearch(): void {
    }

    /**
     * Adds a property into the advanced search.
     *
     * @param name - The search property name
     * @param label - The search property label
     * @param translationValuePrefix - The search property translated prefix value
     * @param type - The search property type
     * @param comparison - The search property comparission value
     * @param defaultValue - The The search property default value
     */
    protected addSearch(name: string, label: string, translationValuePrefix: string, type: PropertyType, comparison: Comparison, defaultValue: any) {
        this.searchProperties.push({name, label, translationValuePrefix, type, comparison, defaultValue});
    }

    /**
     * Gets all items and fills the list.
     */
    listItems(): void {
        this.loading = true;
        this.getService()
            .search(
                this.getServiceURL(),
                this.getPaginationParams(),
                this.currentFilter
            )
            .subscribe(result => {
                    this.totalPages = result.totalPages;
                    this.setItems(result);
                    this.postResult();
                    this.loading = false;
                },
                error => {
                    this.notification.error(error.error ? error.error.message : error.message);
                    this.loading = false;
                });
    }

    /**
     * Set items from result
     */
    protected setItems(result) {
        this.items = result.items;
    }

    /**
     * Executes after the result of list items.
     */
    protected postResult(): void {

    }

    /**
     * Goes to the add components.
     */
    add(): void {
        this.goToAdd();
    }

    /**
     * Goes to the edit components.
     *
     * @param id - The id of the item thats going to be edited
     */
    edit(id): void {
        this.goToEdit(id);
    }

    /**
     * Goes to the view components.
     *
     * @param id - The id of the item thats going to be viewed
     */
    view(id): void {
        this.goToView(id);
    }

    /**
     * Sets the removable ID and checks if the operation has
     * a confirmation or not.
     *
     * If NOT: call the service to delete the item.
     *
     * @param id - The id of the item thats going to be removed
     */
    remove(id): void {
        this.removeId = id;
        if (!this.deleteConfirmation) {
            this.delete();
        } else {
            this.deleteConfirmation.subscribe(ok => {
                this.delete();
            });
        }
    }

    /**
     * Calls the service to delete the item.
     *
     * Notifies by 'toast' at the end: Success or Error.
     *
     * Finally, refreshes the list of items.
     */
    delete(): void {
        this.getService().remove(this.getServiceURL(), this.removeId).subscribe(
            result => {
                this.postDelete();
                this.notification.deleteSuccess();
            },
            error => {
                this.notification.error(
                    error.error ? error.error.message : error.message
                );
            },
            () => {
                /* Finally */
                this.listItems();
            }
        );
    }

    /**
     * Goes to the add components.
     */
    goToAdd(): void {
        this.navigate([this.getRouterURL(), 'create']);
    }

    /**
     * Goes to the edit components.
     *
     * @param id - The id of the item thats going to be edited
     */
    goToEdit(id = null): void {
        this.navigate([this.getRouterURL(), 'edit', id ? id : '']);
    }

    /**
     * Goes to the view components.
     *
     * @param id - The id of the item thats going to be viewed
     */
    goToView(id): void {
        this.navigate([this.getRouterURL(), 'view', id]);
    }

    /**
     * Executes post successful delete.
     */
    protected postDelete(): void {
    }

    /**
     * When the page of pagination changes, executes
     * this method.
     *
     * @param page - The number of the page that got changed to
     */
    onChangePaginator(page: number) {
        this.currentPage = page;
        this.listItems();
    }

    /**
     * When key search field, executes this method.
     */
    onSearch(event): void {
        this.currentFilter = event.filter;
        this.currentSearch = event.search;
        this.listItems();
    }

    /**
     * When the ordering changes, executes this method.
     * @param ordering - The object with the data to change the currentColumn and currentSort values
     */
    onChangeSort(ordering: { column: string; sort: string }): void {
        this.currentColumn = ordering.column;
        this.currentSort = ordering.sort;
        this.listItems();
    }

    /**
     * Set the size of the page.
     *
     * @param size - The value of size selected
     */
    public setPageSize(size: number) {
        this.currentPageSize = size;
        this.onChangePaginator(FIRST_PAGE);
    }

    /**
     * Gets the parameters for pagination.
     *
     * @returns The pagination object values
     */
    protected getPaginationParams() {
        return {
            currentPage: this.currentPage,
            pageSize: this.currentPageSize,
            sort: {
                order: this.currentSort,
                column: this.currentColumn
            },
            search: this.currentSearch
                ? this.currentSearch.trim()
                : this.currentSearch
        };
    }

    /**
     * Check if the list is empty
     *
     * @returns The boolean value for emptness of items array
     */
    get listIsEmpty(): boolean {
        return this.items.length <= 0;
    }

    /**
     * Set items, totalPage and currentPage with the result of search list components
     *
     * @param result - The value recevived by the backend response
     */
    receiveItems(result) {
        this.items = result.items;
        this.totalPages = result.totalPages;
        this.currentPage = 1;
        this.postResult();
    }
}
