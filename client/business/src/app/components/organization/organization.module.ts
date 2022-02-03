import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrganizationListComponent} from './organization-list/organization-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgxPermissionsModule} from 'ngx-permissions';
import {LaddaModule} from 'angular2-ladda';
import {LibCommonModule} from 'common';
import {NgxMaskModule} from 'ngx-mask';
import {OrganizationEditComponent} from './organization-edit/organization-edit.component';
import {ImageCropperModule} from 'ngx-image-cropper';
import {UploadImageComponent} from './upload-image/upload-image.component';
import {ModalImageComponent} from './upload-image/modal-image/modal-image.component';
import {ContributorsComponent} from './organization-edit/contributors/contributors.component';

const routes: Routes = [
    {
        path: '',
        component: OrganizationListComponent,
    },
    {
        path: 'create',
        component: OrganizationEditComponent,
        data: {
            breadcrumb: 'organization.add'
        }
    },
    {
        path: 'edit/:id',
        component: OrganizationEditComponent,
        data: {
            breadcrumb: 'organization.edit'
        }
    }
];

@NgModule({
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
        ImageCropperModule
    ],
    declarations: [
        OrganizationListComponent,
        OrganizationEditComponent,
        UploadImageComponent,
        ModalImageComponent,
        ContributorsComponent
    ]
})
export class OrganizationModule {
}
