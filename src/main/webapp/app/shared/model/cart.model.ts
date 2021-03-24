import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface ICart {
  id?: number;
  descripption?: string;
  giftItems?: IGiftItem[];
}

export const defaultValue: Readonly<ICart> = {};
