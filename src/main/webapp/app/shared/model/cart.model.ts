import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface ICart {
  id?: number;
  descripption?: string;
  giftItem?: IGiftItem;
}

export const defaultValue: Readonly<ICart> = {};
