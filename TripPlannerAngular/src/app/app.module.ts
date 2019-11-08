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


const routes:Routes = [
    { path: '', redirectTo: 'home', pathMatch:'full'},
    { path: 'home', component: HomeComponent},
    { path: 'admin', component: AdminComponent},
    { path: 'user', component: UserComponent}
];

@NgModule({
    imports: [
        BrowserModule, FormsModule, HttpClientModule, ReactiveFormsModule, Ng2SmartTableModule, RouterModule.forRoot(routes), FileUploadModule,NgxPaginationModule,
        ConfirmationPopoverModule.forRoot({
        confirmButtonType:'danger'})
    ],
    declarations: [
        AppComponent, HomeComponent, AdminComponent, UserComponent
		], 

    providers: [{provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService, multi:true}],

    bootstrap: [AppComponent]
})

export class AppModule { }