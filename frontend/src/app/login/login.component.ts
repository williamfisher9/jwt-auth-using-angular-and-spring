import { Component } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DataService } from '../services/data.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: '../forms/style.css'
})
export class LoginComponent {

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
        window.localStorage.setItem("token", response.message); 
        this.dataService.setAuthenticated(true);
        this.router.navigate(["/dashboard"]);
      },
      error: err => {
        console.log(err);
      }
    });

  }
}
