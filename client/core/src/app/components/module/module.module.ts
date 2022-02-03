import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {ModuleListComponent} from './module-list/module-list.component';
import {RouterModule, Routes} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxPermissionsModule} from 'ngx-permissions';
import {LibCommonModule} from 'common';
import {ModuleEditComponent} from './module-edit/module-edit.component';
import {LaddaModule} from 'angular2-ladda';

const routes: Routes = [
    {
        path: '',
        component: ModuleListComponent
    },
    {
        path: 'create', component: ModuleEditComponent,
        data: {
            breadcrumb: 'module.add'
        }
    },

    {
        path: 'edit/:id', component: ModuleEditComponent,
        data: {
            breadcrumb: 'module.edit'
        }
    }
];

@NgModule({
    declarations: [
        ModuleListComponent,
        ModuleEditComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forChild(routes),
        TranslateModule,
        NgxPermissionsModule,
        LibCommonModule,
        LaddaModule,
    ]
})
export class ModuleModule {
}
