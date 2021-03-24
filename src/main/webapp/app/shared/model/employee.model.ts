import { IUser } from 'app/shared/model/user.model';

export interface IEmployee {
  id?: number;
  name?: string;
  age?: number;
  user?: IUser;
}

export const defaultValue: Readonly<IEmployee> = {};
