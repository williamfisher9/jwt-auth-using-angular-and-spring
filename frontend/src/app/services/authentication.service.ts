import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { GenericResponse } from '../interfaces/generic-response';
import { baseUrls } from '../constants/constants';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http : HttpClient) { }

  submitLoginRequest(loginRequest: {username : string, password : string}) : Observable<GenericResponse> {
    return this.http.post<GenericResponse>(baseUrls.login, loginRequest).pipe(map(response => response));
  }

  submitRegisterRequest(user : User) : Observable<GenericResponse>{
    return this.http.post<GenericResponse>(baseUrls.register, user).pipe(map(response => response));
  }

  getAuthToken() : String | null {
    return window.localStorage.getItem("token");
  }
}
