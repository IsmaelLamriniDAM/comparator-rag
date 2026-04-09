import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { loginData } from '../interfaces/loginData';
import { registerData } from '../interfaces/registerData';
import { UserRemain } from '../interfaces/userRemain';
import { ProfileUpdate } from '../interfaces/ProfileUpdate';
import { PasswordUpdateUser } from '../interfaces/PasswordUpdateUser';
import { DataChangeForgottPwd } from '../interfaces/DataChangeForgottPwd';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private WEB_URL = "http://localhost:8091/api/v1/auth";
  user = signal<UserRemain | null>(null);
  private urlChangeInfoUser = 'http://localhost:8091/api/v1/user/updateProfile';
  private urlChangePassword = 'http://localhost:8091/api/v1/user/changePassword';

  constructor(private http: HttpClient) {}
  isLoggedIn = signal(localStorage.getItem('logged') === 'true');

  setLoggedIn(value: boolean) {
    this.isLoggedIn.set(value);
    localStorage.setItem('logged', value ? 'true' : 'false');
  }

  login(data: loginData): Observable<any> {
    return this.http.post(this.WEB_URL + '/login', data, { withCredentials: true });
  }

  register(data: registerData): Observable<any> {
    return this.http.post(this.WEB_URL + '/register', data, { withCredentials: true });
  }

  validate(): Observable<any> {
    return this.http.post(this.WEB_URL + '/validate', {}, { withCredentials: true });
  }

  logout() {
    return this.http.post(this.WEB_URL + '/logout', {}, {withCredentials:true})
  }

  updateProfile(profileUpdate: ProfileUpdate): Observable<any> {
    return this.http.put(this.urlChangeInfoUser, profileUpdate, {withCredentials: true});
  }

  updatePassword(pwdUpdateUser: PasswordUpdateUser): Observable<any> {
    return this.http.put(this.urlChangePassword, pwdUpdateUser, {withCredentials: true})
  }

  validateTokenForgotPassword(token: string): Observable<any> {
    return this.http.get(`${this.WEB_URL}/validate-token?token=${token}`);
  }

  forgottPwd(email: string): Observable<any> {
    return this.http.get(`http://localhost:8091/api/v1/user/forgottenPassword?email=${email}`, { withCredentials: true });
  }

  updatePasswordForgot(dataPwd: DataChangeForgottPwd): Observable<any> {
    return this.http.post(`http://localhost:8091/api/v1/user/reset-pwd`, dataPwd, { withCredentials: true });
  }

  setUser(user: UserRemain) {
    this.user.set({
      name: user.name,
      phoneNumber: user.phoneNumber,
      numComparinsons: user.numComparinsons,
      updatedAt: user.updatedAt
    });
  }

  addCompare() {
      this.user.update(currentUser => currentUser ? { ...currentUser, numComparinsons: currentUser.numComparinsons + 1 }: currentUser
    );
  }

  getNumberCompares(): number {
    return this.user()?.numComparinsons || 0;
  }


  clearUser() {
    this.user.set(null);
  }
}
