import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/service/auth.service';
import { Subscription } from 'rxjs';
import { Company } from 'src/app/model/company';
import { GuestService } from 'src/app/service/guestService/guest.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  companyid: number = 0;
  private routeSub?: Subscription;

  user: User = new User(0, '', '');

  constructor(
    private authService: AuthService,
    private route: Router,
    private route2: ActivatedRoute,
    private guestService: GuestService
  ) {}

  ngOnInit(): void {
    this.username = '';
    this.password = '';

    this.routeSub = this.route2.params.subscribe((params) => {
      this.companyid = params['idc'];
      console.log(this.companyid);
    });
  }

  login() {
    this.user.username = this.username;
    this.user.password = this.password;
    //var paramId = params.get('id');

    this.authService.login(this.user).subscribe(
      (res) => {
        if (res == null) {
          alert('Uername or password is wrong');
          this.ngOnInit();
        } else {
          alert('Login successful');
          localStorage.setItem('token', res.accessToken);
          localStorage.setItem('userId', res.id);
          localStorage.setItem('full-name', res.fullName);
          console.log(res);
          console.log(res.roles[0]);
          if (res.roles[0] === 'ROLE_ADMIN') {
            console.log('admin');
            this.route.navigate(['admin/dashboard']);
          }
          if (res.roles[0] === 'ROLE_GUEST') {
            //code to add here
            console.log('guest');
            if (this.companyid != 0) {
              this.enrollCompany(this.companyid, res.id);
            }
            this.route.navigate(['guest/dashboard']);
          }
          if (res.roles[0] === 'ROLE_EMPLOYEE') {
            console.log('employee');
            this.route.navigate(['employee/dashboard']);
          }
        }
      },
      (err) => {
        alert('Login failed');
        this.ngOnInit();
      }
    );
  }

  enrollCompany(id: number, idGuest: number) {
    let company = new Company();
    company.id = id;
    this.guestService.enrollCompany(idGuest, company).subscribe(
      (res) => {
        console.log('happy coding');
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
