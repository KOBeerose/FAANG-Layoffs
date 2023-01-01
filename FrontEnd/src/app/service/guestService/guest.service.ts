import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company } from 'src/app/model/company';

@Injectable({
  providedIn: 'root',
})
export class GuestService {
  readonly url = 'http://localhost:8082/guest/';
  auth_token = localStorage.getItem('token');
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${this.auth_token}`,
  });

  constructor(private http: HttpClient) {}

  getEnrollmentsByGuest(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'enrollments/' + id, {
      headers: this.headers,
    });
  }

  enrollCompany(id: number, company: Company): Observable<any> {
    return this.http.post<any>(this.url + 'enrollcompany/' + id, company, {
      headers: this.headers,
    });
  }

  getAllCompaniesForGuest(): Observable<any> {
    return this.http.get<any>(this.url + 'companieslist', {
      headers: this.headers,
    });
  }
}
