import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {
    if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == ''){
      this.router.navigate(['/login']);
      return false;
    } 
    
    return true;
  }
}
