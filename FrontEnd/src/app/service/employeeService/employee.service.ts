import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from 'src/app/model/company';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  readonly url = 'http://localhost:8082/employee/';
  readonly flaskUrl = 'http://127.0.0.1:5000/';
  auth_token = localStorage.getItem('token');
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${this.auth_token}`,
  });

  constructor(private http: HttpClient) {}

  updateCompany(company: Company): Observable<any> {
    return this.http.put<any>(this.url + 'updatecompany/' + company.id, company, {
      headers: this.headers,
    });
  }

  addCompany(company: Company) {
    return this.http.post<any>(
      this.url + 'addcompany/' + company.ownerId,
      company,
      { headers: this.headers }
    );
  }

  getCompaniesByEmployee(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'list/' + id, {
      headers: this.headers,
    });
  }

  deleteCompany(id: number): Observable<any> {
    return this.http.delete<any>(this.url + 'delete/' + id, {
      headers: this.headers,
    });
  }

  forcedeleteCompany(id: number): Observable<any> {
    console.log(id);
    return this.http.delete<any>(this.url + 'deleteforced/' + id, {
      headers: this.headers,
    });
  }
  getData(link: string) {
    return this.http.post<any>(
      this.flaskUrl + 'profilefetcher' ,
      {"link":link},
      { headers: this.headers }
    );
  }
  predict(link: string) {
    return this.http.post<any>(
      this.flaskUrl + 'prediction' ,
      {"link":link},
      { headers: this.headers }
    );
  }
  
}
