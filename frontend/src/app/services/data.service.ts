import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  authenticated : BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  isAuthenticated = this.authenticated.asObservable();

  constructor() { }

  setAuthenticated(val : boolean){
    this.authenticated.next(val);
  }
}
