import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'home',
    templateUrl: '../_html/app.home.html'
})

export class HomeComponent implements OnInit {
    
    constructor(private router:Router){}
    ngOnInit(){
        // if(sessionStorage.getItem("role")!= "user"){
        //     this.router.navigate(['/error403'])
        // }
    }
}