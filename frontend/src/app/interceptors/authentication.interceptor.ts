import { HttpHandlerFn, HttpRequest } from '@angular/common/http';

export function authInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
) {
  console.log('running interceptor');

  if (
    window.localStorage.getItem('token') == null ||
    window.localStorage.getItem('token') == ''
  ) {
    req = req.clone({

      headers: req.headers.append('Accept', 'application/json')
      .append('Content-Type', 'application/json')
      .append('Access-Control-Allow-Origin', '*')
      .append('Access-Control-Allow-Headers', 'Content-Type')
      .append('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,DELETE,PUT')
      /*setHeaders: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
      },*/
    });
  } else {
    req = req.clone({
      headers: req.headers.append('Accept', 'application/json')
      .append('Content-Type', 'application/json')
      .append('Access-Control-Allow-Origin', '*')
      .append('Access-Control-Allow-Headers', 'Content-Type')
      .append('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,DELETE,PUT')
      .append('Authorization', `Bearer ${window.localStorage.getItem('token')}`)


      /*setHeaders: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
        'Authorization': `Bearer ${window.localStorage.getItem('token')}`,
      },*/
    });
  }

  return next(req);
}
