import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service.service';
import { catchError, map, of } from 'rxjs';

export const dashboardAuthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return authService.validate().pipe(
    map((user) => {
      authService.setUser(user)
      authService.setLoggedIn(true);
      return true;
    }),
    catchError(() => {
      authService.setLoggedIn(false);
      router.navigate(['/login']);
      return of(false)
    })
  );

};
