import { Company } from './company';

export class Enrollment {
  id: number = 0;
  company?: Company;
  enrollmentDate?: Date;

  constructor(id: number, company: Company, enrollmentDate: Date) {
    this.id = id;
    this.company = company;
    this.enrollmentDate = enrollmentDate;
  }
}
