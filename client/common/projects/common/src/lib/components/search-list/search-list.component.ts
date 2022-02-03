import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {BaseSearchComponent} from '../../interfaces/base-search.component';

@Component({
    selector: 'common-search-list',
    templateUrl: './search-list.component.html',
    styleUrls: ['./search-list.component.scss']
})
export class SearchListComponent extends BaseSearchComponent implements OnChanges {

    @Input() filter: any;

    ngOnChanges(changes: SimpleChanges) {
        if (changes.filter && changes.filter.currentValue) {
            this.filters.push(changes.filter.currentValue);
        }
    }

    executeSearch(searchTerm) {
        const unmaskedValue = searchTerm.trim().replace(/[\.|\/|\-]/g, '');
        super.executeSearch(unmaskedValue);
    }
}
