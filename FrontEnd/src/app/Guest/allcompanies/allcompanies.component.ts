import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/model/company';
import { Enrollment } from 'src/app/model/enrollment';
import { User } from 'src/app/model/user';
import { GuestService } from 'src/app/service/guestService/guest.service';
import { ModalService } from 'src/app/employee/modal/modal.service';

@Component({
  selector: 'app-allcompanies',
  templateUrl: './allcompanies.component.html',
  styleUrls: ['./allcompanies.component.css'],
})
export class AllcompaniesComponent implements OnInit {
  Allcompanies: Company[] = [];
  enrolledCompanies: number[] = [];

  guest: User = new User(
    parseInt(localStorage.getItem('userId')!),
    localStorage.getItem('full-name')!,
    localStorage.getItem('token')!
  );
  companyId = 0;
  companyName: string = '';
  companyDescription: string = '';
  openDate: string = '';
  closeDate: string = '';
  companiesByEmployee: Company[] = [];
  company: Company = new Company();

  constructor(
    private guestService: GuestService,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.guestService.getAllCompaniesForGuest().subscribe(
      (res) => {
        this.Allcompanies = res;
        console.log(res);
      },
      (err) => {
        console.log(err);
      }
    );

    this.guestService.getEnrollmentsByGuest(this.guest.id).subscribe(
      (res) => {
        res.forEach((element: Enrollment) => {
          this.enrolledCompanies.push(element.company!.id);
        });
        console.log(this.enrolledCompanies);
        console.log(res);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  enrollCoure(id: number) {
    let company = new Company();
    company.id = id;
    let guestId = localStorage.getItem('userId');
    this.guestService.enrollCompany(Number(guestId)!, company).subscribe(
      (res) => {
        this.enrolledCompanies.push(id);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  showCompanyWithDetails(id: string, companyId: number) {
    this.company = this.Allcompanies.filter((company) => company.id == companyId)[0];
    this.companyName = this.company.nom;
    this.companyId = companyId;
    this.companyDescription = this.company.description;
    this.openDate = this.company.openDate;
    this.closeDate = this.company.closeDate;
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  checkCompany(id: number) {
    return this.enrolledCompanies.includes(id);
  }
}
