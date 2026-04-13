import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service.service';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return authService.validate().pipe(
    map((user) => {
      authService.setUser(user)
      authService.setLoggedIn(true);
      router.navigate(['/dashboard']);
      return false;
    }),
    catchError(() => {
      authService.setLoggedIn(false);
      return of(true)
    })
  );
};
