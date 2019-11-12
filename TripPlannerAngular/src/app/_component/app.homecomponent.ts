import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../_service/app.authenticationservice';

@Component({
    selector: 'home',
    templateUrl: '../_html/app.home.html'
})

export class HomeComponent implements OnInit {
    user:any = {};
    loggeduser:any = {};
    repeatPassword:string = null;
    constructor(private router:Router, private authenticationService:AuthenticationService){}
    ngOnInit(){
        if(sessionStorage.getItem("role") == "user"){
            this.router.navigate(['/user']).then(()=>window.location.reload());
        }
        else if(sessionStorage.getItem("role") == "admin"){
            this.router.navigate(['/admin']).then(()=>window.location.reload());
        }
    }
    userEmail:string = null;
    userPassword:string = null;
    emailError:string = null;
    validateEmail():boolean{
        let regex = new RegExp(/(?:[a-z0-9!#$%&'*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/i);
        if(regex.test(this.user.userEmail)){
            this.emailError = null;
            return true;
        }
        else{
            this.emailError = "Invalid Email";
            return false;
        } 
    }

    loginemail:string = null;
    validateLoginEmail():boolean{
        console.log("hello");
        let regex = new RegExp(/(?:[a-z0-9!#$%&'*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/i);
        if(regex.test(this.userEmail)){
            console.log("here");    
            this.loginemail = null;
            return true;
        }
        else{
            this.loginemail = "Invalid Email";
            return false;
        } 
    }

    nameError:string = null;
    validateName():boolean{
        let regex = new RegExp(/[A-Z][A-Za-z 0-9]{3,}/gm);
        if(regex.test(this.user.userName)){
            this.nameError = null;
            return true;
        }
        else{
            this.nameError = "Name should start with a capital letter";
            return false;
        } 
    }

    passwordError:string=null;
    validatePassword():boolean{
        if(this.user.userPassword == null){
            this.passwordError = "Password cannot be empty!";
			return false;
        }
        else{
            var pattern = new RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/i);
            if (pattern.test(this.user.userPassword)){
                this.passwordError = null;
                return true;    
            } 
            else {
                this.passwordError = "Invalid Password Format";
                return false;
            }
        }
    }

    loginpass:string=null;
    validateLoginPassword():boolean{
        console.log("Inside")
        if(this.userPassword == null){
            this.loginpass = "Password cannot be empty!";
			return false;
        }
        else{
            var pattern = new RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/i);
            if (pattern.test(this.userPassword)){
                this.loginpass = null;
                return true;    
            } 
            else {
                this.loginpass = "Invalid Password Format";
                return false;
            }
        }
    }

    repeatError:string=null;
    validateRepeatPassword():boolean{
        if(this.validatePassword()){
            if(this.repeatPassword == null){
                this.repeatError = "Password cannot be empty!";
                return false;
            }
            else{
                if(this.repeatPassword === String(this.user.userPassword)){
                    this.repeatError = null;
                    return true;
                }
                else{;
                    this.repeatError = "The passwords do not match";
                    return false;
                }
            }
        }
        return false;
    }

    register(){
        if(this.validateEmail() && this.validateName() && this.validatePassword() && this.validateRepeatPassword()){
            this.authenticationService.register(this.user.userName, this.user.userEmail, this.user.userPassword).subscribe(success=>{alert("User Registered Successfully"); this.router.navigate(['/home']);}, (error)=>alert(error.error));
        }
    }

    login(){
        if(this.validateLoginEmail() && this.validateLoginPassword()){
            this.authenticationService.authenticate(this.userEmail, this.userPassword).subscribe(
                userData => {
                    let tokenStr= 'Bearer '+ userData.token;
                    sessionStorage.setItem('token', tokenStr);
                    sessionStorage.setItem('userEmail',this.userEmail);
                    this.authenticationService.checkRole(this.userEmail).subscribe((data:any)=>{this.loggeduser=data;this.checkRoles();});
                }, error=>{
                    alert("Invalid Credentials");
                    this.router.navigate(['/home']);
                }
            );
        }
    }

    checkRoles(){
        if(this.loggeduser.isAdmin){
            sessionStorage.setItem('role',"admin");
            sessionStorage.setItem("userId",this.loggeduser.userId);
            this.router.navigate(['/admin']).then(() => {
                window.location.reload();
            });
        
        }
        else{
            sessionStorage.setItem('role',"user");
            sessionStorage.setItem("userId",this.loggeduser.userId);
            this.router.navigate(['/user']).then(() => {
                window.location.reload();
            });
        }
    }
}