import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface ICategory {
  id?: number;
  categoryName?: string;
  giftItems?: IGiftItem[];
}

export const defaultValue: Readonly<ICategory> = {};
