import { Component, OnInit } from '@angular/core';
import { Company } from '../model/company';
import { CompanyService } from '../employee/company.service';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { ModalService } from '../employee/modal/modal.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
})
export class LandingPageComponent implements OnInit {
  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: true,
    touchDrag: true,
    pullDrag: true,
    dots: false,
    navSpeed: 700,
    navText: ['<', '>'],
    responsive: {
      0: {
        items: 1,
      },
      400: {
        items: 2,
      },
      740: {
        items: 3,
      },
      940: {
        items: 4,
      },
    },
    nav: true,
  };

  companyId = 0;
  companyName: string = '';
  companyDescription: string = '';
  openDate: string = '';
  closeDate: string = '';
  companyToDelete: number = 0;
  company: Company = new Company();

  Allcompanies: Company[] = [];
  constructor(
    private companyService: CompanyService,
    private modalService: ModalService,
    private route: Router
  ) {}

  ngOnInit(): void {
    this.companyService.getAllCompanies().subscribe(
      (res) => {
        this.Allcompanies = res;
        console.log(res);
      },
      (err) => {
        console.log(err);
      }
    );
    console.log(this.Allcompanies);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  showCompanyWithDetails(id: string, companyId: number) {
    this.company = this.Allcompanies.filter((company) => company.id == companyId)[0];
    console.log(companyId);
    this.companyName = this.company.nom;
    this.companyId = companyId;
    this.companyDescription = this.company.description;
    this.openDate = this.company.openDate;
    this.closeDate = this.company.closeDate;
    console.log(this.company);
    console.log('close date :' + this.closeDate);
    console.log('open date :' + this.openDate);
    this.modalService.open(id);
  }

  EnrollCompanyLP(id: string) {
    this.closeModal(id);
    this.route.navigate(['login/' + this.companyId]);
  }
}
