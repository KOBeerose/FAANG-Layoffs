import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashbordComponent } from './Guest/dashbord/dashbord.component';
import { TdashbordComponent } from './employee/tdashbord/tdashbord.component';
import { CompanyService } from './employee/company.service';
import { AllcompaniesComponent } from './Guest/allcompanies/allcompanies.component';
import { DashboardComponent } from './Admin/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { ModalComponent } from './employee/modal/modal.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { AdminService } from './service/admin.service';
import { EmployeeService } from './service/employeeService/employee.service';
import { GuestService } from './service/guestService/guest.service';
// modules
@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    LoginComponent,
    SignupComponent,
    DashbordComponent,
    TdashbordComponent,
    AllcompaniesComponent,
    DashboardComponent,
    ModalComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatDatepickerModule,
    CarouselModule,
    ReactiveFormsModule,
    MatButtonModule,

    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatNativeDateModule,
    MatRadioModule,
  ],
  providers: [CompanyService, AdminService, EmployeeService, GuestService],
  bootstrap: [AppComponent],
})
export class AppModule {}
