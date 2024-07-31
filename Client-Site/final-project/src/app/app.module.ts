import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { HttpModule} from '@angular/http';
import { AppComponent } from './app.component';

import { HeaderComponent } from './header/header.component';
import { IndexComponent } from './index/index.component';
import { FooterComponent } from './footer/footer.component';
import { MovieListComponent } from './movie-list/movie-list.component';
import { MovieDetailComponent } from './movie-detail/movie-detail.component';
import { TicketComponent } from './ticket/ticket.component';
import { SnackComponent } from './snack/snack.component';
import { FoodmodalComponent } from './snack/foodmodal/foodmodal.component';
import {Router, RouterModule, Routes} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { CheckoutComponent } from './checkout/checkout.component';
import { CheckoutConfirmComponent } from './checkout-confirm/checkout-confirm.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { NewsComponent } from './news/news.component';


const appRoutes: Routes = [
  { path: '', component: IndexComponent},
  { path: 'header', component: HeaderComponent},
  { path: 'news', component: NewsComponent},
  { path: 'home', component: IndexComponent},
  { path: 'movie', component: MovieListComponent},
  { path: 'ticket', component: TicketComponent},
  { path: 'snack', component: SnackComponent},
  { path: 'movieDetail', component: MovieDetailComponent},
  { path: 'checkout', component: CheckoutComponent},
  { path: 'checkoutConfirm', component: CheckoutConfirmComponent },
  { path: 'aboutus', component: AboutusComponent},
];
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    IndexComponent,
    FooterComponent,
    MovieListComponent,
    MovieDetailComponent,
    TicketComponent,
    SnackComponent,
    FoodmodalComponent,
    CheckoutComponent,
    CheckoutConfirmComponent,
    AboutusComponent,
    NewsComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ CookieService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
