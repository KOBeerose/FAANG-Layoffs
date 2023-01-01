export class User {
  id: number = 0;
  fullName: string = '';
  email: string = '';
  username: string = '';
  password: string = '';
  role: string[] = [];
  token: string = '';

  constructor(id: number, fullName: string, token: string) {
    this.id = id;
    this.fullName = fullName;
    this.token = token;
  }
}
