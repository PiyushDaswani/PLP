import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {Routes, RouterModule} from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';

import { FormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination'
import {ConfirmationPopoverModule} from 'angular-confirmation-popover'
 

import {ReactiveFormsModule} from '@angular/forms';
import {  HTTP_INTERCEPTORS } from '@angular/common/http';
import {FileUploadModule} from 'ng2-file-upload';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { HomeComponent } from './_component/app.homecomponent';
import { AdminComponent } from './_component/app.admincomponent';
import { UserComponent } from './_component/app.user.component';
import { BasicAuthHtppInterceptorService } from './_service/app.basicauthinterceptorservice';
import { LogoutComponent } from './_component/app.logoutcomponent';
import { LocationComponent } from './_component/app.locationcomponent';
import { CommonModule } from '@angular/common';
import { BookTripComponent } from './_component/app.booktripcomponent';
import { TicketComponent } from './_component/app.ticketcomponent';
import { AddHotelComponent } from './_component/app.addhotelcomponent';
import { HotelComponent } from './_component/app.hotelcomponent';
import { SingleHotelComponent } from './_component/app.singlehotelcomponent';
import { HotelBookingComponent } from './_component/app.hotelbookingcomponent';
import { HotelTicketComponent } from './_component/app.hotelticketcomponent';
import { MyTicketComponent } from './_component/app.myticketcomponent';
import { Error404Component } from './_component/app.error404component';
import { Error403Component } from './_component/app.error403component';


const routes:Routes = [
    { path: '', redirectTo: 'home', pathMatch:'full'},
    { path: 'home', component: HomeComponent},
    { path: 'admin', component: AdminComponent},
    { path: 'user', component: UserComponent},
    { path: 'logout', component: LogoutComponent},
    { path: 'location/:locationId', component: LocationComponent},
    { path: 'booking/:locationId', component: BookTripComponent},
    { path: 'ticket', component:TicketComponent},
    { path: 'addhotel', component:AddHotelComponent},
    { path: 'hotel', component:HotelComponent},
    { path: 'singlehotel/:hotelId', component:SingleHotelComponent},
    { path: 'hotelbooking/:hotelId', component:HotelBookingComponent},
    { path: 'hotelticket', component:HotelTicketComponent},
    { path: 'myticket', component:MyTicketComponent},
    { path: 'error403' , component:Error403Component},
    { path: '**' , component:Error404Component}
];

@NgModule({
    imports: [
        BrowserModule, FormsModule, HttpClientModule, ReactiveFormsModule, Ng2SmartTableModule, RouterModule.forRoot(routes), FileUploadModule
    ],
    declarations: [
        AppComponent, HomeComponent, AdminComponent, UserComponent, LogoutComponent, LocationComponent,BookTripComponent, TicketComponent, AddHotelComponent,HotelComponent, SingleHotelComponent, HotelBookingComponent,HotelTicketComponent,MyTicketComponent,Error403Component,Error404Component	], 

    providers: [{provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService, multi:true}],

    bootstrap: [AppComponent]
})

export class AppModule { }