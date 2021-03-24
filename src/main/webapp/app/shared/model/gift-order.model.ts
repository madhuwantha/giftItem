import { IUser } from 'app/shared/model/user.model';
import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface IGiftOrder {
  id?: number;
  descripption?: string;
  user?: IUser;
  giftItems?: IGiftItem[];
}

export const defaultValue: Readonly<IGiftOrder> = {};
