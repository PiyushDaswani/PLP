import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { EmailValidator } from '@angular/forms';

export class User{
    
    constructor(public status:string) {}
  
}

export class JwtResponse{
    constructor(public jwttoken:string) {}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient:HttpClient, private router:Router) {}

    authenticate(userEmail, userPassword) {
        return this.httpClient.post<any>("http://"+window.location.hostname+":9011/authenticate",{"userEmail":userEmail,"userPassword":userPassword})
    }
  
    register(name,email,password){
        return this.httpClient.post<any>("http://"+window.location.hostname+":9011/register",{"userName":name,"userEmail":email,"userPassword":password});
    }

    checkRole(email:string){
        return this.httpClient.get("http://"+window.location.hostname+":9011/searchuser?email="+email);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('userEmail')
        return !(user === null)
    }

    logOut() {
        sessionStorage.removeItem('userEmail');
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("role");
        sessionStorage.removeItem("userId");
    }
}