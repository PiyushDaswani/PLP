import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';

@Component({
    selector: 'user',
    templateUrl: '../_html/app.user.html'
})

export class UserComponent implements OnInit {
    
    constructor(private router:Router, private locationService:LocationService){}
    locations:any[] = [];
    user:any = null;
    ngOnInit(){
        // if(sessionStorage.getItem("role")!= "user"){
        //     this.router.navigate(['/error403'])
        // }
        this.locationService.listLocations().subscribe((success:any[]) =>{this.locations = success;console.log(this.locations)});
    }
    
    
}