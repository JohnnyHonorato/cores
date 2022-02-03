import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'business-organization-list',
    templateUrl: './organization-list.component.html',
    styleUrls: ['./organization-list.component.scss']
})
export class OrganizationListComponent implements OnInit {

    public headersColumns = [
        {title: 'organization.fantasy-name', sortable: 'name', col: 'col-md-7', value: 'fantasyName'},
        {title: 'organization.cnpj', sortable: 'governmentCode', col: 'col-md-4', value: 'governmentCode',
            mask: '00.000.000/0000-99', formatType: 'cnpj'}
    ];

    public entity = 'organization';

    constructor() {}

    ngOnInit(): void {}

}

