import {User} from '../models/User';


export class Session {
  private user: User;
  token: string;

  constructor(user: User, token: string) {
    this.user = user;
    this.token = token;
  }
}
