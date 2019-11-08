import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn:'root'
})
export class LocationService{
    constructor(private myhttp:HttpClient){}
    
    listLocations(){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/locations");
    }

}