import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'core-people-list',
    templateUrl: './people-list.component.html',
    styleUrls: ['./people-list.component.scss']
})
export class PeopleListComponent {

    public headersColumns = [
        {
            title: 'common.name',
            value: 'name',
            sortable: 'name',
            col: 'col-md-7'
        },
        {
            title: 'CPF',
            value: 'governmentCode',
            sortable: 'governmentCode',
            col: 'col-md-4',
            formatType: 'cpf'
        }
    ];

    public entity = 'people';

    constructor() {}

}
