import {Component, HostListener, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {BaseListComponent} from '../../interfaces/base-list.component';

@Component({
    selector: 'common-data-table',
    templateUrl: './data-table.component.html',
    styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent extends BaseListComponent implements OnInit, OnChanges {

    @Input() routerURL;

    @Input() serviceURL;

    @Input() modulePrefix = '';

    @Input() columns: any[];

    @Input() currentColumn;

    @Input() entity;

    @Input() showEdit = true;

    @Input() showDelete = true;

    @Input() showAdd = true;

    @Input() defaultFilter: any;

    @Input() actions: any[];

    public formatTypeFunctions;

    innerWidth = 1920;

    constructor() {
        super();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.defaultFilter) {
            this.currentFilter.push(changes.defaultFilter.currentValue);
        }
    }

    ngOnInit(): void {
        this.formatTypeFunctions = {
            cpf: this.cpfFormatType,
            cnpj: this.cnpjFormatType
        };
        super.currentColumn = this.currentColumn;
        super.ngOnInit();
        this.onResize();
    }

    showValue(item, predicate, formatType) {
        if (predicate && item[predicate]) {
            return this.formatByType(item[predicate], formatType);
        }
        return this.formatByType(item, formatType);
    }

    formatByType(value, formatType) {
        if (formatType && value) {
            const formatFunction = this.formatTypeFunctions[formatType];
            return formatFunction ? formatFunction(value) : value;
        }
        return value;
    }

    cpfFormatType(value: string) {
        if (value.length === 11) {
            return `${value.substring(0, 3)}.${value.substring(3, 6)}.${value.substring(6, 9)}-${value.substring(9, 11)}`;
        }
        return value;
    }

    cnpjFormatType(value: string) {
        if (value.length === 14) {
            return `${value.substring(0, 2)}.${value.substring(2, 5)}.${value.substring(5, 8)}/${value.substring(8, 12)}-${value.substring(12, 14)}`;
        }
        return value;
    }

    getRouterURL(): string {
        return this.routerURL;
    }

    getServiceURL(): string {
        return this.serviceURL;
    }

    @HostListener('window:resize', ['$event'])
    onResize(event?) {
        this.innerWidth = window.innerWidth;
    }


}
