import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'user',
    templateUrl: '../_html/app.user.html'
})

export class UserComponent implements OnInit {
    
    constructor(private router:Router){}
    ngOnInit(){
        // if(sessionStorage.getItem("role")!= "user"){
        //     this.router.navigate(['/error403'])
        // }
    }
}