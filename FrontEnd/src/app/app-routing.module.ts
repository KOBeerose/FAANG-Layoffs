import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './Admin/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { AllcompaniesComponent } from './Guest/allcompanies/allcompanies.component';
import { DashbordComponent } from './Guest/dashbord/dashbord.component';
import { TdashbordComponent } from './employee/tdashbord/tdashbord.component';

const routes: Routes = [
  { path: 'home', component: LandingPageComponent },
  { path: '', component: LandingPageComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'login/:idc', component: LoginComponent },
  { path: 'guest/dashboard', component: DashbordComponent },
  { path: 'admin/dashboard', component: DashboardComponent },
  { path: 'guest/allcompanies', component: AllcompaniesComponent },
  { path: 'employee/dashboard', component: TdashbordComponent },
  { path: '**', component: LandingPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
