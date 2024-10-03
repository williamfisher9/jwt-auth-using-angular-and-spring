import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { baseUrls } from '../constants/constants';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class DahsboardService {
  constructor(private http: HttpClient) {}
  getUserDetails(username: string): Observable<any> {

    const headers = new HttpHeaders()
    .set('content-type', 'application/json; charset=utf-8')
    .set('authorization', `Bearer ${localStorage.getItem('token')}`)

      return this.http
      .get<any>(`${baseUrls.dashboard}/${username}`, {headers: headers})
      .pipe(
        map(result => {console.log(result);return result;})
      );
  }
}
