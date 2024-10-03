import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { baseUrls } from '../constants/constants';
import { catchError, map, Observable, of, retry } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root',
})

export class DahsboardService {
  constructor(private http: HttpClient, private authenticationService : AuthenticationService) {}

  getUserDetails(username: string): Observable<unknown> {

    const headers = new HttpHeaders()
    .set('content-type', 'application/json; charset=utf-8')
    .set('authorization', `Bearer ${this.authenticationService.getAuthToken()}`)

      return this.http
      .post<any>(baseUrls.dashboard, { username }, { headers })
      .pipe(map(response => response.username));
  }

}
