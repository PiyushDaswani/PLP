import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { LocationService } from '../_service/app.locationservice';
import { HotelService } from '../_service/app.hotelservice';

@Component({
    selector: 'addhotel',
    templateUrl: '../_html/app.addhotel.html'
})

export class AddHotelComponent implements OnInit {
    exFile:any;
    constructor(private hotelService:HotelService,private service:LocationService,private router:Router){}
    locations:any[] = [];
    chosenLocation:string;
    ngOnInit(){
        if(sessionStorage.getItem("role")!= "admin"){
            this.router.navigate(['/error403']).then(()=>window.location.reload())
        }
        this.service.getLocationNames("").subscribe((data:any[])=> this.locations = data);
    }

    getFileDetails (e:any) {
        console.log(e.target.files);
        for (var i = 0; i < e.target.files.length; i++) { 
            this.exFile = e.target.files[i];
          }
        this.validateExcel();
      }
    
    uploadFiles () {
        if(this.validateId() && this.validateExcel()){
            const frmData = new FormData();
            frmData.append("exfile", this.exFile);
            frmData.append("locationName", this.chosenLocation);
            this.hotelService.addHotel(frmData).subscribe(
            success => {alert(success); this.router.navigate(['/admin']).then(()=>window.location.reload);}, error=>{ alert(error.error); });
        }

    }

    iderror:string=null;
    validateId():boolean{
        if(this.chosenLocation != null){
            this.iderror = null;
            return true;
        }
        else{
            this.iderror = "Choose a location!";
            return false;
        }
    }
    
    file_error = null
    error_file:boolean = false
    validateExcel():boolean{
        if(this.exFile != null){
            var filename = this.exFile.name;
            var extension = filename.substr(filename.lastIndexOf(".")+1);
            if(extension != "xlsx"){
                this.error_file = true;
                this.file_error = "The extension must be .xlsx";
                return false;
            }
            else{
                this.file_error = null
                return true;
            }
        }
        else{
            this.file_error = "You must add a file";
            return false;
        }
    }





}