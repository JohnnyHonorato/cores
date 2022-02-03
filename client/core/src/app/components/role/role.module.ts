import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {NgSelectModule} from '@ng-select/ng-select';
import {RoleComponent} from './role-list/role.component';
import {LaddaModule} from 'angular2-ladda';
import {NgxPermissionsModule} from 'ngx-permissions';
import {LibCommonModule} from 'common';
import {RoleEditComponent} from './role-edit/role-edit.component';

const routes: Routes = [
    {
        path: '',
        component: RoleComponent
    },
    {
        path: 'create', component: RoleEditComponent,
        data: {
            breadcrumb: 'role.add'
        }
    },
    {
        path: 'edit/:id', component: RoleEditComponent,
        data: {
            breadcrumb: 'role.edit'
        }
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        ReactiveFormsModule,
        TranslateModule,
        NgSelectModule,
        LaddaModule,
        NgxPermissionsModule,
        LibCommonModule
    ],
    declarations: [
        RoleComponent,
        RoleEditComponent
    ]
})
export class RoleModule {
}
