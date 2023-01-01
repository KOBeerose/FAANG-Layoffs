import { Component, OnInit } from '@angular/core';
import { Enrollment } from 'src/app/model/enrollment';
import { User } from 'src/app/model/user';
import { GuestService } from 'src/app/service/guestService/guest.service';

@Component({
  selector: 'app-dashbord',
  templateUrl: './dashbord.component.html',
  styleUrls: ['./dashbord.component.css'],
})
export class DashbordComponent implements OnInit {
  //employees: Observable<ApiResponse>;
  enrollments: Enrollment[] = [];
  guest: User = new User(
    parseInt(localStorage.getItem('userId')!),
    localStorage.getItem('full-name')!,
    localStorage.getItem('token')!
  );
  constructor(private guestService: GuestService) {} //private router: Router //private employeeService: EmployeeService,

  ngOnInit() {
    this.guestService.getEnrollmentsByGuest(this.guest.id).subscribe(
      (res) => {
        this.enrollments = res;
        console.log(res);
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
