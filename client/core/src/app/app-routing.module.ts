import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {DashboardComponent} from '@components/dashboard/dashboard.component';
import {SessionExpiredComponent} from '@shared/components/session-expired/session-expired.component';

const routes: Routes = [
    {
        path: '',
        component: DashboardComponent,
        data: {
            title: 'Dashboard',
            breadcrumb: 'Dashboard'
        },
    },
    {
        path: 'people',
        loadChildren: () => import('./components/people/people.module').then(mod => mod.PeopleModule),
        data: {
            title: 'people.title.plural',
            breadcrumb: 'people.title.plural'
        },
    },
    {
        path: 'modules',
        loadChildren: () => import('./components/module/module.module').then(mod => mod.ModuleModule),
        data: {
            title: 'module.title.plural',
            breadcrumb: 'module.title.plural'
        }
    },
    {
        path: 'roles',
        loadChildren: () => import('./components/role/role.module').then(mod => mod.RoleModule),
        data: {
            title: 'role.title.plural',
            breadcrumb: 'role.title.plural'
        }
    },
    {
        path: 'session-expired',
        component: SessionExpiredComponent
    },
    {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
