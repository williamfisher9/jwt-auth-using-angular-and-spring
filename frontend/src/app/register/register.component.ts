import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { ErrorComponent } from '../error/error.component';
import { FormSelectComponent } from "../form-select/form-select.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, ErrorComponent, FormSelectComponent],
  templateUrl: './register.component.html',
  styleUrls: ['../forms/style.css', './register.component.css']
})
export class RegisterComponent implements OnInit{

  displayError : boolean = false;
  errorCode : number = 0;
  errorMessage : string = '';

  roles : any[] = [];


  registerForm = new FormGroup({
    username: new FormControl("hamza.hamdan@hotmail.com"),
    firstName: new FormControl("Hamza"),
    lastName: new FormControl("Hamdan"),
    password: new FormControl("12345678"),
  })

  constructor(private authenticationService : AuthenticationService, private router : Router) {}

  ngOnInit(): void {
    this.authenticationService.getRoles().subscribe(response => {
      if(response.length > 0){
        response.forEach((element: { authority: any; }) => {
          this.roles.push(element.authority);
          console.log(element.authority)
        });
      }
    })
  }
  
  handleRegisterFormSubmission(e : any){
    e.preventDefault();
    this.authenticationService.submitRegisterRequest(new User(this.registerForm.value.username!, 
      this.registerForm.value.firstName!,
      this.registerForm.value.lastName!, 
      this.registerForm.value.password!, [this.selectedValue]))
    .subscribe({
      next: response => {console.log(response); this.router.navigate(['/login'])},
      error: err => {
        console.log(err)
        this.displayError = true;
        this.errorCode = err.status;
        this.errorMessage = err.error
      }
    })
  }

  selectedValue : string = '';

  setSelectedValue(e: string) {
    this.selectedValue = e;
    console.log(e)
    }
}
