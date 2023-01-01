import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company } from '../model/company';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  constructor(private http: HttpClient) {}
  getAllCompanies(): Observable<any> {
    return this.http.get<any>('http://localhost:8082/companies/page');
  }
  
}
