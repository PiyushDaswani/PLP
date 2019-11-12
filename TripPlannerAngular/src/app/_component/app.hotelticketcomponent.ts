import { Component, OnInit} from '@angular/core';
import { UserService } from '../_service/app.userservice';
import {saveAs} from 'file-saver';
import { Router } from '@angular/router';
import { HotelService } from '../_service/app.hotelservice';

@Component({
    selector: 'hotelticket',
    templateUrl: '../_html/app.hotelticket.html'
})

export class HotelTicketComponent implements OnInit{

    header:string;
    constructor(private service:HotelService, private router:Router){
        console.log("In Constructor");
    }

    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
    }
    ticketOne(){
         this.service.getTicket().subscribe(response => {
             const blob = new Blob([response], {type: 'application/pdf'});
             saveAs(blob, 'HotelTicket.pdf');});
    }

    route(){
        this.router.navigate(["/user"]).then(()=>window.location.reload());
    }

}