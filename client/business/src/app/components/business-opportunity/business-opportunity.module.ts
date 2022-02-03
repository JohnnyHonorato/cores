import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {LibCommonModule} from 'common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxMaskModule} from 'ngx-mask';
import {TranslateModule} from '@ngx-translate/core';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgxPermissionsModule} from 'ngx-permissions';
import {LaddaModule} from 'angular2-ladda';
import {BusinessOpportunityListComponent} from './business-opportunity-list/business-opportunity-list.component';
import {BusinessOpportunityEditComponent} from './business-opportunity-edit/business-opportunity-edit.component';

const routes: Routes = [
    {
        path: '',
        component: BusinessOpportunityListComponent
    },
    {
        path: 'create', component: BusinessOpportunityEditComponent,
        data: {
            breadcrumb: 'business-opportunity.add'
        }
    },
    {
        path: 'edit/:id', component: BusinessOpportunityEditComponent,
        data: {
            breadcrumb: 'business-opportunity.edit'
        }
    }
];

@NgModule({
    declarations: [
        BusinessOpportunityListComponent,
        BusinessOpportunityEditComponent
    ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        NgxMaskModule.forRoot(),
        ReactiveFormsModule,
        TranslateModule,
        NgSelectModule,
        NgxPermissionsModule,
        LaddaModule,
        LibCommonModule,
    ]
})
export class BusinessOpportunityModule {
}
