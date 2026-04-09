import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../service/auth-service.service";
import { inject } from "@angular/core";
import { catchError, map, of } from "rxjs";

export const ForgottPwdGuard: CanActivateFn = (route, state) => {
    const router = inject(Router);
    const authService = inject(AuthService);


    return authService.validateTokenForgotPassword(route.queryParams['token']).pipe(
    map(isValid => {
        if (isValid) {
        return true;
        } else {
            router.navigate(['/login']);
            console.log('Token NO VALIDO EN EL FRINT POR ALGUNA RAZON');
            return false;
        }
        }),
        catchError(() => {
            router.navigate(['/login']);
            return of(false);
        })
    );
};
