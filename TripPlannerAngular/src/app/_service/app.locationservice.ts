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

    getLocation(id:number){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/location/find?locationId="+id); 
    }

    getLocationNames(locationName:string){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/location/names?locationName="+locationName); 
    }

    listHotels(){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/hotels?locationId="+sessionStorage.getItem("locationId"));
    }

}