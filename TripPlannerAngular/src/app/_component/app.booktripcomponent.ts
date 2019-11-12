import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { DomSanitizerImpl, DomSanitizer } from '@angular/platform-browser/src/security/dom_sanitization_service';
import { Booking } from '../_model/app.booking';
import { TransportService } from '../_service/app.transportservice';

@Component({
    selector: 'booktrip',
    templateUrl: '../_html/app.booktrip.html'
})

export class BookTripComponent implements OnInit {
    
    constructor(private router:Router, private route: ActivatedRoute, private service:LocationService, private transportService:TransportService){}
    locationId:any;
    location:any = {};
    forwardFlight:any[];
    reverseFlight:any[];   
    forwardId:any;
    reverseId:any;
    transports:string[] = ["Flight-cab", "Flight-bus", "Train-cab", "Train-bus"];
    mode:string ;
    locations:string[];
    userList:any = [];
    chosenLocation:string;
    seats:any;
    transportType:string;
    numbers:number[];
    dateOfTravel:any;
    returnDate:Date;
    
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "user"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.locationId=+this.route.snapshot.paramMap.get("locationId");
        this.service.getLocation(this.locationId).subscribe(data => {this.location = data, this.service.getLocationNames(this.location.locationName).subscribe((data:string[])=>this.locations=data);});
    }

    modeError:string = null
    validateMode(){
        if(this.mode != null){
            this.modeError = null;
            return true;
        }
        else{
            this.modeError = "You need to choose a transport Mode";
            return false;
        }
    }

    locationError:string = null
    validateChosenLocation(){
        if(this.chosenLocation != null){
            this.locationError = null;
            return true;
        }
        else{
            this.locationError = "You need to choose a location";
            return false;
        }
    }

    dateError:string = null
    validateDate(){
        let today = new Date(new Date().toDateString());
        today.setDate(today.getDate() + 90);
        console.log(today);
        if(this.dateOfTravel != null){
            if(new Date(this.dateOfTravel) <= new Date(new Date().toDateString())){
                this.dateError = "You cannot choose a date from the past";
                return false;
            }
            else if(new Date(this.dateOfTravel) > today){
                this.dateError = "You cannot choose a date more than 90 days into the future";
                return false;
            }
            else{
                this.dateError = null;
                return true;
            }
        }
        else{
            this.dateError = "You need to choose a date";
            return false;
        }
    }

    seatsError:string = null;
    validateSeats(){
        if(this.seats != null){
            if(this.seats >10){
                this.seatsError = "You cant book more than 10 seats at a time";
                return false;
            }
            else if(this.seats <=0){
                this.seatsError = "You cant choose 0 or negative seats";
                return false;
            }
            else{
                this.seatsError = null;
                return true;
            }
        }
        else{
            this.seatsError = "Enter the number of seats"
        }
    }

    userListError:string = null;
    validateUserList(){ 
        let error = false;
        let regex = new RegExp(/[A-Z][A-Za-z 0-9]{3,}/gm);
        if(this.userList.includes(undefined)){
            this.userListError = "Enter all the user details";
            return false;
        }
        this.userList.forEach(user => {
            if(!regex.test(user)){
                error = true;
                this.userListError = "The names should start with a capital letter and have at least 3 characters"
            }
        });
        if(error == false){
            this.userListError = null;
            return true;
        }
        else{
            return false;
        }
    }

    forwardFlightError:string = null;
    validateForwardFlight(){
        if(this.forwardId == null){
            this.forwardFlightError = "You must select a Flight";
            return false;
        }
        else{
            this.forwardFlightError = null;
            return true;
        }
    }

    reverseFlightError:string = null;
    validatereverseFlight(){
        if(this.reverseId == null){
            this.reverseFlightError = "You must select a Flight";
            return false;
        }
        else{
            this.reverseFlightError = null;
            return true;
        }
    }

    update(){
        this.userList = Array(this.seats);
    }

    change(){
        if(this.validateMode()){
            if(this.mode.startsWith("Flight")){
                this.transportType = "Flight";
            }
            else{
                this.transportType = "Train";
            }
        }
    }

    showTransport(){
        if(this.validateMode() && this.validateChosenLocation() && this.validateDate() && this.validateSeats() && this.validateUserList()){
        this.transportService.getFlights(this.location.locationName, this.chosenLocation, this.transportType).subscribe((data:any[]) => {this.forwardFlight = data; console.log(this.forwardFlight)} );
        this.transportService.getFlights(this.chosenLocation,this.location.locationName , this.transportType).subscribe((data:any[]) => {this.reverseFlight = data; console.log(this.reverseFlight)} );
        }
    }

    book(){
        if(this.validateMode() && this.validateChosenLocation() && this.validateDate() && this.validateSeats() && this.validateUserList() && this.validateForwardFlight() && this.validatereverseFlight()){
            const form = new FormData();
            form.append("userId", sessionStorage.getItem("userId"));
            form.append("bookingDate", this.dateOfTravel);
            form.append("bookingSeats", this.seats);
            form.append("transportId", this.forwardId);
            form.append("bookedUserList", JSON.stringify(this.userList));
            form.append("locationId", this.locationId);
            const formSecond = new FormData();
            this.returnDate = new Date(this.dateOfTravel);
            console.log(this.returnDate);
            this.returnDate.setDate(this.returnDate.getDate() + this.location.tripDuration);
            this.dateOfTravel = this.returnDate.toISOString().slice(0,10);
            console.log(this.dateOfTravel); 
            formSecond.append("userId", sessionStorage.getItem("userId"));
            formSecond.append("bookingDate", this.dateOfTravel);
            formSecond.append("bookingSeats", this.seats);
            formSecond.append("transportId", this.reverseId);
            formSecond.append("bookedUserList", JSON.stringify(this.userList));
            formSecond.append("locationId", this.locationId);

            this.transportService.bookFlight(form).subscribe(success =>{
            this.transportService.bookFlight(formSecond).subscribe(success =>{alert("Travel Booked Successfully"); sessionStorage.setItem("locationId", this.locationId); this.router.navigate(['/ticket']).then(()=>window.location.reload())},error=>console.log(error.error))},
            error =>console.log(error.error));
        }
    }

}