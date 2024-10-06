import { Component } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DataService } from '../services/data.service';
import { AuthenticationService } from '../services/authentication.service';
import { ErrorComponent } from '../error/error.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, ErrorComponent],
  templateUrl: './login.component.html',
  styleUrls: ['../forms/style.css', './login.component.css'] 
})
export class LoginComponent {
  displayError : boolean = false;
  errorCode : number = 0;
  errorMessage : string = '';

  loginForm = new FormGroup({
    username: new FormControl("hamza.hamdan@hotmail.com"),
    password: new FormControl("12345678")
  })

  constructor(private authenticationService : AuthenticationService, private router : Router, private dataService : DataService) {}

  handleLoginFormSubmission(e : any) {
    e.preventDefault();
    
    this.authenticationService.submitLoginRequest({username: this.loginForm.value.username!, password: this.loginForm.value.password!})
    .subscribe({
      next: response => {
        console.log(response)
        window.localStorage.setItem("token", response.message); 
        this.dataService.setAuthenticated(true);
        this.router.navigate(["/dashboard"]);
      },
      error: err => {
        if(err.status === 401){
          this.displayError = true;
          this.errorCode = 401;
          this.errorMessage = "Invalid username or password!"
        }
      }
    });

  }
}
