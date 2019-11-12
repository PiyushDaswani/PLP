import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'admin',
    templateUrl: '../_html/app.error403.html'
})

export class Error403Component{
    constructor(private router:Router) {           
    }

    redirect(){
        if(sessionStorage.getItem("role")!= null){
            if(sessionStorage.getItem("role") == "user"){
                this.router.navigate(['/user']).then(()=>window.location.reload());
            }
            else{
                this.router.navigate(['/admin']).then(()=>window.location.reload());
            }
        }
        else{
            this.router.navigate(['/home']).then(()=>window.location.reload());
        }
    }

}