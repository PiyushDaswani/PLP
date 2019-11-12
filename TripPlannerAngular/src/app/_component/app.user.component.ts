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
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.locationService.listLocations().subscribe((success:any[]) =>{this.locations = success;console.log(this.locations)});
    }
    
    route(id:number){
        this.router.navigate(['/location',id]).then(() => {
            window.location.reload();
        });;
    }
}