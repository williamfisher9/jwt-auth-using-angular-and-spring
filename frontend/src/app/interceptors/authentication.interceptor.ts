import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  console.log('running interceptor');

  const authToken = inject(AuthenticationService).getAuthToken();

  if (
    window.localStorage.getItem('token') == null || window.localStorage.getItem('token') == ''
  ) {
    req = req.clone({
      setHeaders: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'
      },
    });
  } else {
    req = req.clone({
      setHeaders: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
        'Authorization': `Bearer ${window.localStorage.getItem('token')}`
      },
    });
  }

  return next(req);
}
