import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from './not-found.component';
import { RouterModule, Routes } from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';

const routes: Routes = [
  {
      path: '',
      component: NotFoundComponent
  }
];
@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    RouterModule.forChild(routes),
  ],
  declarations: [NotFoundComponent]
})
export class NotFoundModule {
}
