import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { DomSanitizerImpl, DomSanitizer } from '@angular/platform-browser/src/security/dom_sanitization_service';

@Component({
    selector: 'hotel',
    templateUrl: '../_html/app.hotel.html'
})

export class HotelComponent implements OnInit {
    
    constructor(private router:Router, private service:LocationService){}
    hotels:any[] = [];
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.service.listHotels().subscribe((data:any[])=>{this.hotels = data; console.log(this.hotels)}, error=> alert(error.error));
    }

    route(hotelId){
        this.router.navigate(['/singlehotel', hotelId]).then(()=>window.location.reload());
    }



}