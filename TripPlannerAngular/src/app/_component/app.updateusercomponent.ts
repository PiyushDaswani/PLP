import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';

@Component({
    selector: 'updateuser',
    templateUrl: '../_html/app.updateuser.html'
})

export class UpdateUserComponent implements OnInit {
    
    constructor(private router:Router, private locationService:LocationService){}
    locations:any[] = [];
    user:any = null;
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.locationService.listLocations().subscribe((success:any[]) =>{this.locations = success;console.log(this.locations)});
    }
    
    
}