import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  loginUrl: string = '';
  signUpUrl: string = '';

  constructor(private http: HttpClient) {
    this.loginUrl = 'http://localhost:8082/api/auth/signin';
    this.signUpUrl = 'http://localhost:8082/api/auth/signup';
  }

  login(user: User): Observable<any> {
    return this.http.post<any>(this.loginUrl, user);
  }

  signUp(user: User): Observable<any> {
    return this.http.post<any>(this.signUpUrl, user);
  }
}
