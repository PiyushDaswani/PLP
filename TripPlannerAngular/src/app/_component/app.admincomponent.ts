import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'admin',
    templateUrl: '../_html/app.admin.html'
})

export class AdminComponent implements OnInit {
    
    constructor(private router:Router){}
    ngOnInit(){
        // if(sessionStorage.getItem("role")!= "user"){
        //     this.router.navigate(['/error403'])
        // }
    }
}