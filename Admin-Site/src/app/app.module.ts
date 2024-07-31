import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { HttpModule} from '@angular/http';



import { AppComponent } from './app.component';

import { FroalaEditorModule, FroalaViewModule } from 'angular-froala-wysiwyg';
import {Router, RouterModule, Routes} from '@angular/router';
import { CustomerPanelComponent } from './customer-panel/customer-panel.component';
import { EmployeePanelComponent } from './employee-panel/employee-panel.component';
import { MoviePanelComponent } from './movie-panel/movie-panel.component';
import { MovieShowpanelComponent } from './movie-showpanel/movie-showpanel.component';
import { OrderPanelComponent } from './order-panel/order-panel.component';
import { SnackPanelComponent } from './snack-panel/snack-panel.component';
import { NewPanelComponent } from './new-panel/new-panel.component';
import { LoginComponent } from './login/login.component';
import { MovieRevenueComponent } from './movie-revenue/movie-revenue.component';

const appRoutes: Routes = [
  { path: 'customer', component: CustomerPanelComponent},
  { path: 'employee', component: EmployeePanelComponent},
  { path: 'movie', component: MoviePanelComponent},
  { path: 'movieshow', component: MovieShowpanelComponent},
  { path: 'order', component: OrderPanelComponent},
  { path: 'snack', component: SnackPanelComponent},
  { path: 'new', component: NewPanelComponent},
  { path: '', component: LoginComponent},
  { path: 'movierevenue', component: MovieRevenueComponent}
  
];

@NgModule({
  declarations: [
    AppComponent,
    CustomerPanelComponent,
    EmployeePanelComponent,
    MoviePanelComponent,
    MovieShowpanelComponent,
    OrderPanelComponent,
    SnackPanelComponent,
    NewPanelComponent,
    LoginComponent,
    MovieRevenueComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
