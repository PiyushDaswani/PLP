import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn:'root'
})
export class TransportService{
    constructor(private myhttp:HttpClient){}
    
    getFlights(departureFrom, arrivalAt, mode){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/transports?departure=" + departureFrom + "&arrival="+arrivalAt + "&mode=" + mode);
    }

    bookFlight(form){
        console.log("here");
        return this.myhttp.post("http://"+ window.location.hostname+":9011/user/booktrip", form);
    }

}