import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {EmptyRouteComponent} from './components/empty-route/empty-route.component';
import {BoardEditComponent} from './components/board-edit/board-edit.component';
import {BoardListComponent} from './components/board-list/board-list.component';
import {BoardViewComponent} from './components/board-view/board-view.component';
import {TrackerEditComponent} from './components/board-view/tracker-edit/tracker-edit.component';
import {TrackerViewComponent} from './components/board-view/tracker-view/tracker-view.component';

const routes: Routes = [
    {
        path: 'tracker',
        children: [
            {
                path: '', component: BoardListComponent
            },
            {
                path: 'create', component: BoardEditComponent
            },
            {
                path: ':id', component: BoardViewComponent
            },
            {
                path: 'edit/:id', component: BoardEditComponent
            },
            {
                path: ':id/status/:statusId/card/create', component: TrackerEditComponent
            },
            {
                path: ':id/status/:statusId/card/view/:idCard', component: TrackerViewComponent
            },
            {
                path: ':id/status/:statusId/card/edit/:idCard', component: TrackerEditComponent
            },
        ]
    },
    {
        path: '**',
        component: EmptyRouteComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
