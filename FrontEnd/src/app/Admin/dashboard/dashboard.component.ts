import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  Employees: User[] = [];
  Guests: User[] = [];
  admin: User = new User(
    parseInt(localStorage.getItem('userId')!),
    localStorage.getItem('full-name')!,
    localStorage.getItem('token')!
  );
  constructor(private adminService: AdminService, private router: Router) {} //private router: Router //private employeeService: EmployeeService,

  ngOnInit(): void {
    console.log(this.admin);
    this.adminService.getAllEmployees().subscribe(
      (res) => {
        this.Employees = res;
        console.log(res);
      },
      (err) => {
        this.router.navigate(['/login']);
      }
    );

    this.adminService.getAllGuests().subscribe(
      (res) => {
        this.Guests = res;
        console.log(res);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  deleteGuest(id: number) {
    this.adminService.deleteGuest(id).subscribe(
      (res) => {
        console.log(res);
        this.ngOnInit();
      },
      (err) => {
        console.log(err);
      }
    );
  }
  deleteemployee(id: number) {
    this.adminService.deleteEmployee(id).subscribe(
      (res) => {
        console.log(res);
        this.ngOnInit();
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
