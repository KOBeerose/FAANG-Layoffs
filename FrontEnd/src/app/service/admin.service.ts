import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  readonly url = 'http://localhost:8082/admin/';
  auth_token = localStorage.getItem('token');
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${this.auth_token}`,
  });
  constructor(private http: HttpClient) {}

  getAllEmployees(): Observable<any> {
    return this.http.get<any>(this.url + 'employees', { headers: this.headers });
  }

  getAllGuests(): Observable<any> {
    return this.http.get<any>(this.url + 'guests', { headers: this.headers });
  }

  deleteGuest(id: number): Observable<any> {
    return this.http.delete<any>(this.url + 'deleteguest/' + id, {
      headers: this.headers,
    });
  }
  deleteEmployee(id: number): Observable<any> {
    return this.http.delete<any>(this.url + 'deleteemployee/' + id, {
      headers: this.headers,
    });
  }
}
