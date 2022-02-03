import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {SpaHostComponent} from './spa-host.component';

const routes: Routes = [
    {
        path: '',
        component: SpaHostComponent,
    }
];

@NgModule({
    declarations: [SpaHostComponent],
    imports: [CommonModule, RouterModule.forChild(routes)]
})
export class SpaHostModule {
}
