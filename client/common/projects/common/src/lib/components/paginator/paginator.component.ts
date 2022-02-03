import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

export const FIRST_PAGE = 1;

/**
 * The Paginantion components for navigates in the application.
 *
 * Provides the data by pages.
 */
@Component({
    selector: 'common-paginator',
    templateUrl: './paginator.component.html',
    styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent implements OnInit {

    /**
     * Event of "onPageChanged" to notify the components that
     * a page was changed.
     */
    @Output() pageChanged = new EventEmitter<any>();

    /**
     * Total pages available.
     */
    private quantityPages = 1;

    /**
     * Maximum number of pages to show.
     */
    maxPages = 5;

    /**
     * The current page of the components.
     */
    @Input()
    currentPage = 1;

    /**
     * Last page of pagination.
     */
    last = 1;

    /**
     * Array to control the pages to be showed.
     */
    pages = [];

    /**
     * On Init of the components.
     */
    ngOnInit(): void {
        this.updatePages();
    }

    /**
     * Updates all attributes based on the total pages and current page.
     */
    updatePages(): void {
        this.last = this.totalPages;

        const fitAll = this.totalPages <= this.maxPages;

        let firstIndex = fitAll ? 1 : this.currentPage - 2;
        let lastIndex = fitAll ? this.totalPages : this.currentPage + 2;

        if (firstIndex < FIRST_PAGE) {
            firstIndex = FIRST_PAGE;
            lastIndex = lastIndex + (lastIndex - FIRST_PAGE);

        } else if (lastIndex > this.totalPages) {
            firstIndex = firstIndex - (lastIndex - this.totalPages);
            lastIndex = this.totalPages;
        }

        let index = firstIndex;
        this.pages = [];

        for (let i = 0; i < this.maxPages; i++) {
            if (index <= lastIndex) {
                this.pages[i] = index++;
            }
        }
    }

    /**
     * Changes the page and emits the "onPageChange" event.
     *
     * @param page - The page that got clicked
     */
    changePage(page) {
        if (page !== this.currentPage) {
            this.currentPage = page;
            this.updatePages();
            this.pageChanged.emit(page);
        }
    }

    /**
     * Goes to the first page.
     */
    firstPage() {
        this.changePage(FIRST_PAGE);
    }

    /**
     * Goes to the last page.
     */
    lastPage() {
        this.changePage(this.last);
    }

    /**
     * Gets the total of pages available.
     *
     * @returns The totalPages attribute value.
     */
    get totalPages() {
        return this.quantityPages;
    }

    /**
     * Sets the total of pages available.
     *
     * @param value - The number of pages
     */
    @Input()
    set totalPages(value) {
        this.quantityPages = value;

        this.updatePages();
    }

}
