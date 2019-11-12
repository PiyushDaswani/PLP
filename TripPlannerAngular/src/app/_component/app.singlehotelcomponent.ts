import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { DomSanitizerImpl, DomSanitizer } from '@angular/platform-browser/src/security/dom_sanitization_service';
import { HotelService } from '../_service/app.hotelservice';

@Component({
    selector: 'singlehotel',
    templateUrl: '../_html/app.singlehotel.html'
})

export class SingleHotelComponent implements OnInit {
    
    constructor(private router:Router, private route: ActivatedRoute, private service:HotelService){}
    hotelId:number;
    hotel:any = {};   
    images:string[] = []
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.hotelId=+this.route.snapshot.paramMap.get("hotelId");
        this.service.getHotel(this.hotelId).subscribe(data => {this.hotel = data;
            this.images.push("url(assets/img/".concat(String(this.hotel.images[0]) + ")"));
            this.images.push("url(assets/img/".concat(String(this.hotel.images[1]) + ")"));
            this.images.push("url(assets/img/".concat(String(this.hotel.images[2]) + ")"));
            },error=> alert(error.error));
    }

    routeBooking(){
        this.router.navigate(['/hotelbooking', this.hotelId]).then(()=>window.location.reload());
    }
}