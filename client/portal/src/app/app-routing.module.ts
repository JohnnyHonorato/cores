import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SessionExpiredComponent} from './components/session-expired/session-expired.component';
import {MainComponent} from './components/main/main.component';

const routes: Routes = [
    {
        path: '',
        component: MainComponent
    },
    {
        path: 'session-expired',
        component: SessionExpiredComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
