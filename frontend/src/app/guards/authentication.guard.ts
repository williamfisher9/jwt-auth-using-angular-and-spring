import { CanActivateFn } from '@angular/router';

export const authenticationGuard: CanActivateFn = (route, state) => {

  if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == ''){
    return false;
  }

  return true;
};
