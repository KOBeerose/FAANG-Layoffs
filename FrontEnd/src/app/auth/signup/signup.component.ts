import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  firstName: string = '';
  lastName: string = '';
  username: string = '';
  password: string = '';
  confirmpassword: string = '';
  role: string = '';
  email: string = '';

  user: User = new User(0, '', '');

  constructor(private authService: AuthService, private route: Router) {}

  ngOnInit(): void {
    this.username = '';
    this.password = '';
    this.confirmpassword = '';
    this.firstName = '';
    this.lastName = '';
    this.role = 'employee';
    this.email = '';
    this.user.role = [];
  }

  onSelected(value: string): void {
    this.role = value;
    console.log(value);
  }

  signup() {
    this.user.username = this.username;
    this.user.password = this.password;
    this.user.fullName = this.firstName + ' ' + this.lastName;
    this.user.role.push(this.role);
    this.user.email = this.username;
    console.log(this.role);
    console.log(this.user);
    if (
      this.user.username == '' ||
      this.user.password == '' ||
      this.user.fullName == '' ||
      this.user.email == '' ||
      this.confirmpassword == ''
    ) {
      alert('Please fill all the fields');
    }
    if (this.password === this.confirmpassword) {
      this.authService.signUp(this.user).subscribe(
        (res) => {
          if (res == null) {
            alert('Registration failed');
            this.ngOnInit();
          } else {
            console.log('Registration successful');
            alert('Registration successful');
            this.route.navigate(['/login']);
          }
        },
        (err) => {
          alert('Registration failed.');
          this.ngOnInit();
        }
      );
    } else {
      alert('password and confirm password don t match');
    }
  }
}
