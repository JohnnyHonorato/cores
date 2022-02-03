import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {DashboardComponent} from '@components/dashboard/dashboard.component';

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
        path: 'organizations',
        loadChildren: () => import('./components/organization/organization.module').then(mod => mod.OrganizationModule),
        data: {
            title: 'organization.title.plural',
            breadcrumb: 'organization.title.plural'
        },
    },
    {
        path: 'business-oportunities',
        loadChildren: () => import('./components/business-opportunity/business-opportunity.module')
            .then(mod => mod.BusinessOpportunityModule),
        data: {
            title: 'business-opportunity.title.plural',
            breadcrumb: 'business-opportunity.title.plural'
        },
    },
    {
        path: 'not-found',
        loadChildren: () => import('./shared/components/not-found/not-found.module')
            .then(mod => mod.NotFoundModule)
    },
    {
        path: 'tracker',
        children: [
            {
                path: '**',
                loadChildren: () => import('./shared/spa-host/spa-host.module').then(m => m.SpaHostModule),
                data: {
                    app: 'tracker',
                    title: 'board.title.plural',
                    breadcrumb: 'board.title.plural'
                }
            },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
