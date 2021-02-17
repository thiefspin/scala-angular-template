export class User {
  private id: number;
  private email: String;
  private name: String;
  private surname: String;
  private created: string;
  private lastLogin?: string;

  constructor(id: number, email: string, name: string, surname: string, password: string, created: string) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.created = created;
  }
}
