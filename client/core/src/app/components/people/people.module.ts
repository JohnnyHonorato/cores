import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PeopleListComponent} from './people-list/people-list.component';
import {RouterModule, Routes} from '@angular/router';
import {LibCommonModule} from 'common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgxPermissionsModule} from 'ngx-permissions';
import {LaddaModule} from 'angular2-ladda';
import {PeopleEditComponent} from './people-edit/people-edit.component';
import {NgxMaskModule} from 'ngx-mask';

const routes: Routes = [
    {
        path: '',
        component: PeopleListComponent
    },
    {
        path: 'create', component: PeopleEditComponent,
        data: {
            breadcrumb: 'people.add'
        }
    },
    {
        path: 'edit/:id', component: PeopleEditComponent,
        data: {
            breadcrumb: 'people.edit'
        }
    }
];

@NgModule({
    declarations: [
        PeopleListComponent,
        PeopleEditComponent
    ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        ReactiveFormsModule,
        TranslateModule,
        NgSelectModule,
        NgxPermissionsModule,
        LaddaModule,
        LibCommonModule,
        NgxMaskModule,
    ]
})
export class PeopleModule {
}
