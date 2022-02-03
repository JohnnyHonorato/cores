import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'core-module-list',
    templateUrl: './module-list.component.html',
    styleUrls: ['./module-list.component.scss']
})
export class ModuleListComponent implements OnInit {

    public headersColumns = [
        {title: 'common.name', sortable: 'name', col: 'col-md-7', value: 'name'},
        {
            title: 'module.table-header.open-mode',
            sortable: 'openMode',
            col: 'col-md-4',
            value: 'openMode'
        }
    ];

    public entity = 'module';

    constructor() {}

    ngOnInit(): void {
    }

}
