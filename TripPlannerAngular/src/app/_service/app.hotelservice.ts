import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn:'root'
})
export class HotelService{
    constructor(private myhttp:HttpClient){}

    addHotel(frmData){
        return this.myhttp.post("http://"+ window.location.hostname+":9011/admin/hotel/add", frmData);
    }

    getHotel(id){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/hotel/find?hotelId="+ id);
    }

    bookHotel(form){
        return this.myhttp.post("http://"+ window.location.hostname+":9011/user/hotel/book", form);
    }
    getTicket(){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/hotel/ticket?userid="+ sessionStorage.getItem("userId"), {
        responseType: 'blob'
        });
    }
}