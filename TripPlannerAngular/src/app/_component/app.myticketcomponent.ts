import { Component, OnInit} from '@angular/core';
import { UserService } from '../_service/app.userservice';
import {saveAs} from 'file-saver';
import { Router } from '@angular/router';
import { HotelService } from '../_service/app.hotelservice';

@Component({
    selector: 'myticket',
    templateUrl: '../_html/app.myticket.html'
})

export class MyTicketComponent implements OnInit{

    header:string;
    constructor(private hotelService:HotelService,private service:UserService, private router:Router){
        
    }

    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload());
        }
    }
    ticketOne(){
         this.service.getTicket(0).subscribe(response => {
             const blob = new Blob([response], {type: 'application/pdf'});
             saveAs(blob, 'Ticket1.pdf');});
    }
    ticketTwo(){
        this.service.getTicket(1).subscribe(response => {
            const blob = new Blob([response], {type: 'application/pdf'});
            saveAs(blob, 'Ticket2.pdf');});
    }

    hotelTicket(){
        this.hotelService.getTicket().subscribe(response => {
            const blob = new Blob([response], {type: 'application/pdf'});
            saveAs(blob, 'HotelTicket.pdf');});
    }
    
}