import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequest} from './auth/LoginRequest';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private authApi: string = '/api/auth';

  constructor(private http: HttpClient) {

  }

  public login(req: LoginRequest) {
    return this.http
      .post(`${this.authApi}/login`, JSON.stringify(req), this.postHeaders())
      .pipe(
        map(res => res)
      );
  }

  public isAuthenticated() {
    console.log(localStorage.getItem('session'))
  }


  private postHeaders() {
    return {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    };
  }
}
