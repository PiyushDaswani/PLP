import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { DomSanitizerImpl, DomSanitizer } from '@angular/platform-browser/src/security/dom_sanitization_service';

@Component({
    selector: 'location',
    templateUrl: '../_html/app.location.html'
})

export class LocationComponent implements OnInit {
    
    constructor(private router:Router, private route: ActivatedRoute, private service:LocationService){}
    locationId:number;
    location:any = {};   
    images:string[] = []
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.locationId=+this.route.snapshot.paramMap.get("locationId");
        this.service.getLocation(this.locationId).subscribe(data => {this.location = data;
            this.images.push("url(assets/img/".concat(String(this.location.locationImages[0]) + ")"));
            this.images.push("url(assets/img/".concat(String(this.location.locationImages[1]) + ")"));
            this.images.push("url(assets/img/".concat(String(this.location.locationImages[2]) + ")"));
            },error=> alert(error.error));
    }

    routeBooking(){
        console.log("hello");
        this.router.navigate(['/booking', this.locationId]).then(()=>window.location.reload());
    }
}