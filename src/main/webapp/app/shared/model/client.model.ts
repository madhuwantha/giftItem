import { IUser } from 'app/shared/model/user.model';

export interface IClient {
  id?: number;
  name?: string;
  addressLineOne?: string;
  addressLineTwo?: string;
  city?: string;
  postalCode?: number;
  user?: IUser;
}

export const defaultValue: Readonly<IClient> = {};
