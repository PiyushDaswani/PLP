import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { DomSanitizerImpl, DomSanitizer } from '@angular/platform-browser/src/security/dom_sanitization_service';
import { Booking } from '../_model/app.booking';
import { TransportService } from '../_service/app.transportservice';
import { HotelService } from '../_service/app.hotelservice';

@Component({
    selector: 'hotelbooking',
    templateUrl: '../_html/app.hotelbooking.html'
})

export class HotelBookingComponent implements OnInit {
    
    constructor(private router:Router, private route: ActivatedRoute, private service:HotelService, private transportService:TransportService){}
    hotelId:any;
    hotel:any;
    rooms:any;

    checkIn = ["12:00:00", "18:00:00"];
    checkInTime:string;
    checkOutTime:string;
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.hotelId=+this.route.snapshot.paramMap.get("hotelId");
        this.service.getHotel(this.hotelId).subscribe(data => {this.hotel = data});
    }

    roomsError:string = null;
    validateRooms(){
        if(this.rooms == null){
            this.roomsError = "Choose the number of rooms to be booked";
            return false;
        }
        else{
            if(this.rooms>5){
                this.roomsError = "You can book upto 5 rooms only";
                return false;
            }
            else if(this.rooms < 0){
                this.roomsError = "You really thought you can sell rooms? No negative values allowed"
                return false;
            }
            else{
                this.roomsError = null;
                return true;
            }
        }
    }

    checkInError:string = null;
    validateCheckIn(){
        if(this.checkInTime != null){
            this.checkInError = null;
            return true;
        }
        else{
            this.checkInError = "Choose a value!";
            return false;
        }
    }

    checkOutError:string = null;
    validateCheckOut(){
        if(this.checkOutTime != null){
            this.checkOutError = null;
            return true;
        }
        else{
            this.checkOutError = "Choose a value!";
            return false;
        }
    }

    book(){
        if(this.validateCheckIn() && this.validateCheckOut() && this.validateRooms()){
            const form = new FormData();
            form.append("userId", sessionStorage.getItem("userId"));
            form.append("hotelId", this.hotelId);
            form.append("checkIn", this.checkInTime);
            form.append("checkOut", this.checkOutTime);
            form.append("rooms", this.rooms);
            this.service.bookHotel(form).subscribe(success=>{alert(success);this.router.navigate(['/hotelticket']).then(()=>window.location.reload());},error=>alert(error.error));
        }
    }

}