import { Component, OnInit} from '@angular/core';
import { UserService } from '../_service/app.userservice';
import {saveAs} from 'file-saver';
import { Router } from '@angular/router';

@Component({
    selector: 'ticket',
    templateUrl: '../_html/app.ticket.html'
})

export class TicketComponent implements OnInit{

    header:string;
    constructor(private service:UserService, private router:Router){
        console.log("In Constructor");
    }

    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
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

    route(){
        this.router.navigate(["/hotel"]).then(()=>window.location.reload());
    }

}