import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn:'root'
})
export class UserService{
    constructor(private myhttp:HttpClient){}
    
    updateUser(){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/update");
    }

    getTicket(ticketNo){
        return this.myhttp.get("http://"+ window.location.hostname+":9011/user/ticket?userid="+ sessionStorage.getItem("userId")+"&ticketNo="+ticketNo, {
        responseType: 'blob'
        });
    }

}