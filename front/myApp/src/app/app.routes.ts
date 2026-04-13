import { Routes } from '@angular/router';
import { Landing } from './pages/landing/landing';
import { Register } from './components/auth/register/register';
import { Login } from './components/auth/login/login';
import { LoginPage } from './pages/login/login';
import { RegisterPage } from './pages/register/register';
import { ForgotPasswordPage } from './pages/forgot-password/forgot-password';
import { ChangePasswordPage } from './pages/change-password/change-password';
import { Dashboard } from './pages/dashboard/dashboard';
import { SuccesPagesComponent } from './pages/logout/succes-page/succes-pages.component';
import { ErrorPageComponent } from './pages/logout/error-page/error-page.component';
import { authGuard } from './guards/auth-guard';
import { dashboardAuthGuard } from './guards/dashboard-auth-guard';
import { ForgottPwdGuard } from './guards/forgott-pwd-guard';

export const routes: Routes = [
   {
        path: '',
        component: Landing
    },
    {
        path: 'login',
        canActivate: [authGuard],
        component: LoginPage
    },
    {
        path: 'register',
        canActivate: [authGuard],
        component: RegisterPage
    },
    {
        path: 'forgot-password',
        component: ForgotPasswordPage
    },
    {
        path: 'change-password',
        canActivate: [authGuard],
        component: ChangePasswordPage
    },
    {
        path: 'dashboard',
        canActivate: [dashboardAuthGuard],
        component: Dashboard
    },
    {
        path: 'logout/succes',
        component: SuccesPagesComponent
    },
    {
        path: 'logout/error',
        component: ErrorPageComponent
    },
    {
        path: 'reset-pwd',
        canActivate: [ForgottPwdGuard],
        component: ChangePasswordPage
    },
    {
        path: '**',
        redirectTo: ''
    }
];
