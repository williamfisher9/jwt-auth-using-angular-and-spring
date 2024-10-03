import { Component } from '@angular/core';
import { User } from '../model/user';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['../forms/style.css', './register.component.css']
})
export class RegisterComponent {

  registerForm = new FormGroup({
    username: new FormControl("hamza.hamdan@hotmail.com"),
    firstName: new FormControl("Hamza"),
    lastName: new FormControl("Hamdan"),
    password: new FormControl("12345678"),
  })

  constructor(private authenticationService : AuthenticationService, private router : Router) {}

  handleRegisterFormSubmission(e : any){
    e.preventDefault();
    this.authenticationService.submitRegisterRequest(new User(this.registerForm.value.username!, 
      this.registerForm.value.firstName!,
      this.registerForm.value.lastName!, 
      this.registerForm.value.password!, 
      ['ADMIN']))
    .subscribe({
      next: response => {console.log(response); this.router.navigate(['/login'])},
      error: err => {console.log(err)}
    })
  }
}
